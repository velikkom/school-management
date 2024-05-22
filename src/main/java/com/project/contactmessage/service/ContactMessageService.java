package com.project.contactmessage.service;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper createContactMessage;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage = createContactMessage.requestToContactMessage(contactMessageRequest);
        ContactMessage savedContactMessage = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully ")
                .httpStatus(HttpStatus.CREATED)
                .object(createContactMessage.contactMessageToResponse(savedContactMessage))
                .build();
    }

    public Page<ContactMessageResponse> getAllByPage(Pageable pageRequest) {
        Page<ContactMessage> contactMessages = contactMessageRepository.findAll(pageRequest);
        return contactMessages.map(createContactMessage::contactMessageToResponse);
    }

    public ResponseMessage<Page<ContactMessageResponse>> searchByEmailByPage(String email, Pageable pageRequest) {
        // Email boş veya null ise hata fırlat
        if (!StringUtils.hasText(email)) {
            return ResponseMessage.<Page<ContactMessageResponse>>builder()
                    .message("Email cannot be empty")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Email formatı kontrolü
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseMessage.<Page<ContactMessageResponse>>builder()
                    .message("Invalid email format")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Veritabanında email içeren mesajları ara
        Page<ContactMessage> contactMessages = contactMessageRepository.findByEmailContaining(email, pageRequest);

        // Sonuçlar boşsa bilgi mesajı döndür
        if (contactMessages.isEmpty()) {
            return ResponseMessage.<Page<ContactMessageResponse>>builder()
                    .message("No contact messages found with the provided email")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        // Sonuçları DTO'ya dönüştür
        Page<ContactMessageResponse> responsePage = contactMessages.map(createContactMessage::contactMessageToResponse);


        // Başarı mesajı ile sonuçları döndür
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }

    public ResponseMessage<Page<ContactMessageResponse>> searchBySubjectByPage(String subject, Pageable pageRequest) {
        // Subject boş veya null ise hata fırlat
        if (!StringUtils.hasText(subject)) {
            return ResponseMessage.<Page<ContactMessageResponse>>builder()
                    .message("Subject cannot be empty")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Veritabanında subject içeren mesajları ara
        Page<ContactMessage> contactMessages = contactMessageRepository.findBySubjectContaining(subject, pageRequest);

        // Sonuçlar boşsa bilgi mesajı döndür
        if (contactMessages.isEmpty()) {
            return ResponseMessage.<Page<ContactMessageResponse>>builder()
                    .message("No contact messages found with the provided subject")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        // Sonuçları DTO'ya dönüştür
        Page<ContactMessageResponse> responsePage = contactMessages.map(createContactMessage::contactMessageToResponse);

        // Başarı mesajı ile sonuçları döndür
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }

    public ResponseMessage<List<ContactMessageResponse>> searchByDateBetween(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        List<ContactMessage> contactMessageList = contactMessageRepository.findByDateTimeBetween(startDateTime, endDateTime);
        if (contactMessageList.isEmpty()) {
            return ResponseMessage.<List<ContactMessageResponse>>builder()
                    .message("No contact messages found between the provided dates")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        List<ContactMessageResponse> responseList = contactMessageList.stream()
                .map(createContactMessage::contactMessageToResponse)
                .collect(Collectors.toList());
        return ResponseMessage.<List<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responseList)
                .build();
    }

}






