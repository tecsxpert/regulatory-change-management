package com.internship.tool.repository;

import com.internship.tool.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
}
