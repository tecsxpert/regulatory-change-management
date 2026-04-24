package com.internship.tool.service;

import com.internship.tool.dto.FileUploadResponse;
import com.internship.tool.entity.FileAttachment;
import com.internship.tool.exception.InputValidationException;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.FileAttachmentRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileAttachmentService {

    private static final long MAX_FILE_SIZE_BYTES = 10L * 1024L * 1024L;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "image/png",
            "image/jpeg",
            "text/plain",
            "text/csv",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-excel"
    );

    private final FileAttachmentRepository fileAttachmentRepository;
    private final Path storageRoot;

    public FileAttachmentService(
            FileAttachmentRepository fileAttachmentRepository,
            @Value("${app.file-storage-path:${FILE_STORAGE_PATH:./uploads}}") String fileStoragePath
    ) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.storageRoot = Paths.get(fileStoragePath).toAbsolutePath().normalize();
        createStorageDirectoryIfMissing();
    }

    public FileUploadResponse upload(MultipartFile file) {
        validateFile(file);

        String originalFilename = file.getOriginalFilename() == null ? "unknown" : file.getOriginalFilename();
        String storedFilename = buildUuidFilename(originalFilename);
        Path targetPath = storageRoot.resolve(storedFilename);

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new InputValidationException("Failed to store file");
        }

        FileAttachment attachment = new FileAttachment();
        attachment.setOriginalFilename(originalFilename);
        attachment.setStoredFilename(storedFilename);
        attachment.setContentType(file.getContentType());
        attachment.setSizeBytes(file.getSize());
        attachment.setStoragePath(targetPath.toString());

        FileAttachment saved = fileAttachmentRepository.save(attachment);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Resource getFileResource(Long id) {
        FileAttachment attachment = fileAttachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found for id: " + id));

        Path filePath = Paths.get(attachment.getStoragePath());
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("Stored file missing for id: " + id);
        }
        return new FileSystemResource(filePath);
    }

    @Transactional(readOnly = true)
    public FileAttachment getMetadata(Long id) {
        return fileAttachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found for id: " + id));
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InputValidationException("File is required");
        }

        if (file.getSize() >= MAX_FILE_SIZE_BYTES) {
            throw new InputValidationException("File size must be less than 10 MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new InputValidationException("Unsupported file type");
        }
    }

    private String buildUuidFilename(String originalFilename) {
        String extension = "";
        int lastDot = originalFilename.lastIndexOf('.');
        if (lastDot > -1 && lastDot < originalFilename.length() - 1) {
            extension = originalFilename.substring(lastDot);
        }
        return UUID.randomUUID() + extension;
    }

    private FileUploadResponse toResponse(FileAttachment attachment) {
        FileUploadResponse response = new FileUploadResponse();
        response.setId(attachment.getId());
        response.setOriginalFilename(attachment.getOriginalFilename());
        response.setStoredFilename(attachment.getStoredFilename());
        response.setContentType(attachment.getContentType());
        response.setSizeBytes(attachment.getSizeBytes());
        return response;
    }

    private void createStorageDirectoryIfMissing() {
        try {
            Files.createDirectories(storageRoot);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not create file storage directory", exception);
        }
    }
}
