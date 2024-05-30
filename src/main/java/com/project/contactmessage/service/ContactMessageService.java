package com.project.contactmessage.service;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.messages.Messages;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully ")
                .httpStatus(HttpStatus.CREATED)
                .object(contactMessageMapper.contactMessageToResponse(savedData))
                .build();
        /**
         * Bu sınıftaneler yaptık
         * 1->ContactMessage contactMessage = createContactMessage.requestToContactMessage(contactMessageRequest); methodu ile
         * dto-pojo dönüşümü sağladık
         * 2->ContactMessage savedData = contactMessageRepository.save(contactMessage); repo ya gönderdik
         * 3->return ResponseMessage.<ContactMessageResponse>builder()
         *                 .message("Contact Message Created Successfully ")
         *                 .httpStatus(HttpStatus.CREATED)
         *                 .object(createContactMessage.contactMessageToResponse(savedData))
         *                 .build()
         * gelen cevap pojo olduğu için DTO ya dönüştürüp geri cevap verdik.
         */
    }

   /* public Page<ContactMessageResponse> getAllByPage(Pageable pageRequest) {

        Page<ContactMessage> contactMessages = contactMessageRepository.findAll(pageRequest);
        return contactMessages.map(contactMessageMapper::contactMessageToResponse);
    }*/
   public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {

       Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
       if(Objects.equals(type, "desc")){
           pageable = PageRequest.of(page,size, Sort.by(sort).descending());
       }

       return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);
   }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findByEmailEquals(email, pageable).
                map(contactMessageMapper::contactMessageToResponse);
    }

   /* public ResponseMessage<Page<ContactMessageResponse>> searchByEmailByPage(String email, Pageable pageRequest) {
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
        Page<ContactMessageResponse> responsePage = contactMessages.map(contactMessageMapper::contactMessageToResponse);


        // Başarı mesajı ile sonuçları döndür
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }
*/
  /* public ResponseMessage<Page<ContactMessageResponse>> searchBySubjectByPage(String subject, Pageable pageRequest) {
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
        Page<ContactMessageResponse> responsePage = contactMessages.map(contactMessageMapper::contactMessageToResponse);

        // Başarı mesajı ile sonuçları döndür
        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responsePage)
                .build();
    }
*/

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findBySubjectEquals(subject, pageable). // Derived
                map(contactMessageMapper::contactMessageToResponse);
    }



   /* public ResponseMessage<List<ContactMessageResponse>> searchByDateBetween(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        List<ContactMessage> contactMessageList = contactMessageRepository.findByDateTimeBetween(startDateTime, endDateTime);
        if (contactMessageList.isEmpty()) {
            return ResponseMessage.<List<ContactMessageResponse>>builder()
                    .message("No contact messages found between the provided dates")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        List<ContactMessageResponse> responseList = contactMessageList.stream()
                .map(contactMessageMapper::contactMessageToResponse)
                .collect(Collectors.toList());
        return ResponseMessage.<List<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responseList)
                .build();
    }*/

    public List<ContactMessage> searchBetweenDates(String beginDateString, String endDateString)
    {
        try {
            LocalDate beginDate = LocalDate.parse(beginDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_MESSAGE);
        }
    }




    /*public void deleteById(Long id) {
        if (!contactMessageRepository.existsById(id)){
            throw new ResourceNotFoundException("Contact message with ID " + id + " not found.");
        }
        contactMessageRepository.deleteById(id);
    }*/

    public Object deleteById(Long contactMessageId)
    {
        getContactMessageById(contactMessageId);
        contactMessageRepository.deleteById(contactMessageId);
        return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }

    public ContactMessage getContactMessageById(Long id){
        return contactMessageRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));
    }



    public ContactMessageResponse findById(Long id) {
        ContactMessage contactMessage = contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact message with ID " + id + " not found."));
        return contactMessageMapper.contactMessageToResponse(contactMessage);
    }


    public List<ContactMessage> searchBetweenTimes(String startHourString, String startMinuteString, String endHourString, String endMinuteString) {

        try {
            int startHour = Integer.parseInt(startHourString);
            int startMinute = Integer.parseInt(startMinuteString);
            int endHour = Integer.parseInt(endHourString);
            int endMinute = Integer.parseInt(endMinuteString);

            return contactMessageRepository.findMessagesBetweenTimes(startHour,startMinute,endHour,endMinute);
        } catch (NumberFormatException e) {
            throw new ConflictException(Messages.WRONG_TIME_MESSAGE);
        }
    }
   /* public ResponseMessage<List<ContactMessageResponse>> searchByTimeBetween(String startTime, String endTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);

        List<ContactMessage> contactMessageList = contactMessageRepository.findByDateTimeBetween(startDateTime, endDateTime);
        if (contactMessageList.isEmpty()) {
            return ResponseMessage.<List<ContactMessageResponse>>builder()
                    .message("No contact messages found between the provided times")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        List<ContactMessageResponse> responseList = contactMessageList.stream()
                .map(contactMessageMapper::contactMessageToResponse)
                .collect(Collectors.toList());
        return ResponseMessage.<List<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(responseList)
                .build();
    }*/










  /*  public ContactMessageResponse findById(Long id) {
     if (!contactMessageRepository.existsById(id)){
         throw new ResourceNotFoundException("Contact message with ID " + id + " not found.");
     }
     return contactMessageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact message with ID " + id + " not found."));
        //.
    }*/
}






