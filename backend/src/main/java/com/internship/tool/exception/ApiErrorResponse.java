package com.internship.tool.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Standard JSON error response")
public class ApiErrorResponse {

    @Schema(example = "2026-04-30T10:30:00")
    private LocalDateTime timestamp;
    @Schema(example = "404")
    private int status;
    @Schema(example = "Not Found")
    private String error;
    @Schema(example = "Regulatory change not found for id: 99")
    private String message;
    @Schema(example = "/api/v1/regulatory-changes/99")
    private String path;

    public ApiErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
