package com.project.contactmessage.repository;

import com.project.contactmessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {
    Page<ContactMessage> findByEmailContaining(String email, Pageable pageRequest);

    Page<ContactMessage> findBySubjectContaining(String subject, Pageable pageRequest);

    List<ContactMessage> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

 /*   Page<ContactMessage> findByEmailContaining(String email, Pageable pageRequest);

    Page<ContactMessage> findBySubjectContaining(String subject, Pageable pageRequest);*/




}
