package com.internship.tool.controller;

import com.internship.tool.dto.FileUploadResponse;
import com.internship.tool.entity.FileAttachment;
import com.internship.tool.service.FileAttachmentService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileAttachmentController {

    private final FileAttachmentService fileAttachmentService;

    public FileAttachmentController(FileAttachmentService fileAttachmentService) {
        this.fileAttachmentService = fileAttachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        FileUploadResponse response = fileAttachmentService.upload(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        FileAttachment metadata = fileAttachmentService.getMetadata(id);
        Resource resource = fileAttachmentService.getFileResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + metadata.getOriginalFilename() + "\"")
                .contentLength(metadata.getSizeBytes())
                .body(resource);
    }
}
