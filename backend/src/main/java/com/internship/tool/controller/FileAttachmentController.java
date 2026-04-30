package com.internship.tool.controller;

import com.internship.tool.dto.FileUploadResponse;
import com.internship.tool.entity.FileAttachment;
import com.internship.tool.service.FileAttachmentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Upload a file attachment", description = "Uploads a file, validates type and size, stores it with a UUID filename, and returns metadata.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileUploadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file type, size, or missing file"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        FileUploadResponse response = fileAttachmentService.upload(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/files/{id}")
    @Operation(summary = "Download a file attachment", description = "Returns the stored file content by attachment id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File returned successfully",
                    content = @Content(mediaType = "application/octet-stream",
                            schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
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
