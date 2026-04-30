package com.internship.tool.controller;

import com.internship.tool.dto.RegulatoryChangeRequest;
import com.internship.tool.dto.RegulatoryChangeResponse;
import com.internship.tool.service.RegulatoryChangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get paginated regulatory changes", description = "Returns all regulatory changes using pageable query parameters.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Page of regulatory changes returned successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RegulatoryChangeResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<Page<RegulatoryChangeResponse>> getAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<RegulatoryChangeResponse> response = regulatoryChangeService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get regulatory change by id", description = "Returns a single regulatory change by its identifier.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Regulatory change returned successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RegulatoryChangeResponse.class))),
        @ApiResponse(responseCode = "404", description = "Regulatory change not found"),
        @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<RegulatoryChangeResponse> getById(@PathVariable Long id) {
        RegulatoryChangeResponse response = regulatoryChangeService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    @Operation(summary = "Create regulatory change", description = "Creates a new regulatory change record from a validated request body.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Regulatory change created successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RegulatoryChangeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validation failed for the request body"),
        @ApiResponse(responseCode = "409", description = "Duplicate regulatory change exists"),
        @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<RegulatoryChangeResponse> create(@Valid @RequestBody RegulatoryChangeRequest request) {
        RegulatoryChangeResponse response = regulatoryChangeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
