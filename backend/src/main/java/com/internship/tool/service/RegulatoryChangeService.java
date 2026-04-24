package com.internship.tool.service;

import com.internship.tool.dto.RegulatoryChangeRequest;
import com.internship.tool.dto.RegulatoryChangeResponse;
import java.util.List;

public interface RegulatoryChangeService {

    RegulatoryChangeResponse create(RegulatoryChangeRequest request);

    RegulatoryChangeResponse update(Long id, RegulatoryChangeRequest request);

    RegulatoryChangeResponse getById(Long id);

    List<RegulatoryChangeResponse> getAll();

    void delete(Long id);
}
