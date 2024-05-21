package com.project.contactmessage.repository;

import com.project.contactmessage.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {

}
