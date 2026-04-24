package com.internship.tool.controller;

import com.internship.tool.dto.RegulatoryChangeRequest;
import com.internship.tool.dto.RegulatoryChangeResponse;
import com.internship.tool.service.RegulatoryChangeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regulatory-changes")
public class RegulatoryChangeController {

    private final RegulatoryChangeService regulatoryChangeService;

    public RegulatoryChangeController(RegulatoryChangeService regulatoryChangeService) {
        this.regulatoryChangeService = regulatoryChangeService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<RegulatoryChangeResponse>> getAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<RegulatoryChangeResponse> response = regulatoryChangeService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegulatoryChangeResponse> getById(@PathVariable Long id) {
        RegulatoryChangeResponse response = regulatoryChangeService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<RegulatoryChangeResponse> create(@Valid @RequestBody RegulatoryChangeRequest request) {
        RegulatoryChangeResponse response = regulatoryChangeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
