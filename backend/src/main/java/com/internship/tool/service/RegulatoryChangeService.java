package com.internship.tool.service;

import com.internship.tool.dto.RegulatoryChangeRequest;
import com.internship.tool.dto.RegulatoryChangeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegulatoryChangeService {

    RegulatoryChangeResponse create(RegulatoryChangeRequest request);

    RegulatoryChangeResponse update(Long id, RegulatoryChangeRequest request);

    RegulatoryChangeResponse getById(Long id);

    Page<RegulatoryChangeResponse> getAll(Pageable pageable);

    void delete(Long id);
}
