package com.internship.tool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class RegulatoryChangeRequest {

    @NotBlank(message = "title is required")
    @Size(max = 255, message = "title must be at most 255 characters")
    private String title;

    @NotBlank(message = "description is required")
    @Size(max = 5000, message = "description must be at most 5000 characters")
    private String description;

    @NotBlank(message = "source is required")
    @Size(max = 255, message = "source must be at most 255 characters")
    private String source;

    @NotBlank(message = "jurisdiction is required")
    @Size(max = 120, message = "jurisdiction must be at most 120 characters")
    private String jurisdiction;

    @NotBlank(message = "category is required")
    @Size(max = 120, message = "category must be at most 120 characters")
    private String category;

    @NotBlank(message = "status is required")
    @Size(max = 40, message = "status must be at most 40 characters")
    private String status;

    @NotBlank(message = "priority is required")
    @Size(max = 40, message = "priority must be at most 40 characters")
    private String priority;
    private LocalDate effectiveDate;
    private LocalDate publishedDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
