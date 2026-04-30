package com.internship.tool.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Metadata returned after file upload")
public class FileUploadResponse {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "policy.pdf")
    private String originalFilename;
    @Schema(example = "8f3a3f39-0b21-4e79-a26f-2b1cf2e5a1aa.pdf")
    private String storedFilename;
    @Schema(example = "application/pdf")
    private String contentType;
    @Schema(example = "102400")
    private Long sizeBytes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStoredFilename() {
        return storedFilename;
    }

    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }
}
