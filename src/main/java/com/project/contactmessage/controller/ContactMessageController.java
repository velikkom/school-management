package com.project.contactmessage.controller;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/contactMessages")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    /**
     * http://localhost:8080/contactMessage/save + Json +Post
     */
    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> saveContact(@Valid @RequestBody ContactMessageRequest contactMessageRequest) {
        return contactMessageService.save(contactMessageRequest);
    }

    //

    /**
     * Not: ******************************************** getAllByPage ***************************************
     * http://localhost:8080/contactMessage/getAll
     */
    /*@GetMapping("/getAllByPage")
public ResponseEntity<PagedResponse<ContactMessageResponse>> getAllByPage(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sort") String prop,
        @RequestParam("direction") Sort.Direction direction) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
    PagedResponse<ContactMessageResponse> pagedResponse = contactMessageService.getAllByPage(pageRequest);
    return ResponseEntity.ok(pagedResponse);
}*/
     /*  @GetMapping("/getAllByPage")
    public ResponseMessage<Page<ContactMessageResponse>> getAllByPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<ContactMessageResponse> pagedResponse = contactMessageService.getAllByPage(pageRequest);

        return ResponseMessage.<Page<ContactMessageResponse>>builder()
                .message("Contact messages retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(pagedResponse)
                .build();
    }*/
    @GetMapping("/getAll") // http://localhost:8080/contactMessages/getAll + GET
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.getAll(page, size, sort, type);
    }

    // Not: ************************************* searchByEmailByPage ***************************************

    /**
     * http://localhost:8080/contactMessage/searchByEmailByPage?email= + email + &page= + page + &size= + size + &sort= + prop + &direction= + direction
     *
     * @param email
     * @param page
     * @param size
     * @param prop
     * @param direction
     * @return ResponseEntity<PagedResponse < ContactMessageResponse>>
     */
   /* @GetMapping("/searchByEmailByPage")
    public ResponseEntity<PagedResponse<ContactMessageResponse> > searchByEmailByPage(
            @RequestParam("email") String email,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
        PagedResponse<ContactMessageResponse> pagedResponse = contactMessageService.searchByEmailByPage(email, pageRequest);
        return ResponseEntity.ok(pagedResponse);
    }*/
    @GetMapping("/searchByEmail")
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchByEmail(email, page, size, sort, type);
    }

    // Not: ************************************* searchBySubjectByPage ***************************************

    /**
     * http://localhost:8080/contactMessage/searchBySubjectByPage?subject= + subject + &page= + page + &size= + size + &sort= + prop + &direction= + direction
     *
     * @param subject
     * @param page
     * @param size
     * @param prop
     * @param direction
     * @return ResponseEntity<PagedResponse < ContactMessageResponse>>
     */
   /* @GetMapping("/searchBySubjectByPage")
    public ResponseEntity<PagedResponse<ContactMessageResponse>> searchBySubjectByPage(
            @RequestParam("subject") String subject,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
        PagedResponse<ContactMessageResponse> pagedResponse = contactMessageService.searchBySubjectByPage(subject, pageRequest);
        ResponseMessage<PagedResponse<ContactMessageResponse>> responseMessage = ResponseMessage.<PagedResponse<ContactMessageResponse>>builder()
                .message("Contact messages with subject '" + subject + "' retrieved successfully")
                .httpStatus(HttpStatus.OK)
                .object(pagedResponse)
                .build();

        return ResponseEntity.ok(responseMessage.getObject());
    }*/
   /* @GetMapping("/searchBySubjectByPage")
    public ResponseMessage<Page<ContactMessageResponse>> searchBySubjectByPage(
            @RequestParam("subject") String subject,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
        return contactMessageService.searchBySubjectByPage(subject, pageRequest);
    }*/
    @GetMapping("/searchBySubject")
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchBySubject(subject, page, size, sort, type);
    }
    // Not: **************************************searchByDateBetween ***************************************

    /**
     * http://localhost:8080/contactMessage/searchByDateBetween?startDate= + startDate + &endDate= + endDate
     */
/*    @GetMapping("/searchByDateBetween")
    public ResponseMessage<List<ContactMessageResponse>> searchByDateBetween(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return contactMessageService.searchByDateBetween(startDate, endDate);
    }*/
    @GetMapping("/searchBetweenDates")
    //http://localhost:8080/contactMessages/searchBetweenDates?beginDate=2023-09-13&endDate=2023-09-15   + GET
    public ResponseEntity<List<ContactMessage>> searchBetweenDates(
            @RequestParam(value = "beginDate") String beginDateString,
            @RequestParam(value = "endDate") String endDateString
    ) {
        List<ContactMessage> contactMessages = contactMessageService.searchBetweenDates(beginDateString, endDateString);
        return ResponseEntity.ok(contactMessages);
    }


    // Not:****************************************searchByTimeBetween ***************************************

    /**
     * http://localhost:8080/contactMessage/searchByTimeBetween
     */
/*    @GetMapping("/searchByTimeBetween")
    public ResponseMessage<List<ContactMessageResponse>> searchByTimeBetween(
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        return contactMessageService.searchByTimeBetween(startTime, endTime);
    }*/
    @GetMapping("/searchBetweenTimes")
//http://localhost:8080/contactMessages/searchBetweenTimes?startHour=09&startMinute=00&endHour=17&endMinute=30  + GET
    public ResponseEntity<List<ContactMessage>> searchBetweenTimes(
            @RequestParam(value = "startHour") String startHourString,
            @RequestParam(value = "startMinute") String startMinuteString,
            @RequestParam(value = "endHour") String endHourString,
            @RequestParam(value = "endMinute") String endMinuteString

    ) {
        List<ContactMessage> contactMessages = contactMessageService.searchBetweenTimes(startHourString, startMinuteString, endHourString, endMinuteString);
        return ResponseEntity.ok(contactMessages);
    }

    // @GetMapping("/")
    // Not: *********************************** deleteByIdParam ***************************************

    /**
     * http://localhost:8080/contactMessage/deleteByIdParam
     */
 /*   @DeleteMapping("/deleteById/{contactMessageId}")
    public ResponseMessage<String> deleteByIdParam(@RequestParam("id") Long id){
        contactMessageService.deleteById(id);
        return ResponseMessage.<String>builder()
                .message("Contact message deleted successfully")
                .httpStatus(HttpStatus.OK)
                .object("Contact message with ID " + id + " deleted.")
                .build();
    }*/
    @DeleteMapping("/deleteById/{contactMessageId}")
    public ResponseEntity deleteByIdPath(@PathVariable Long contactMessageId) {
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }

    // Not: ***************************************** deleteById ***************************************
   /* @DeleteMapping("/deleteById/{id}")
    public ResponseMessage<String>deleteById(@PathVariable("id")Long id){
        contactMessageService.deleteById(id);
        return ResponseMessage.<String>builder()
                .message("Contact message deleted successfully")
                .httpStatus(HttpStatus.OK)
                .object("Contact message with ID " + id + " deleted")
                .build();
    }*/
    // Not: Odev2:deleteByIdParam ********************************************
    @DeleteMapping("/deleteByIdParam")  //http://localhost:8080/contactMessages/deleteByIdParam?contactMessageId=2
    public ResponseEntity<String> deleteById(@RequestParam(value = "contactMessageId") Long contactMessageId) {
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId).toString()); // servisdeki ayni metod
    }

    // Not: *********************************** getByIdWithParam ***************************************
    @GetMapping("/getByIdParam")//http://localhost:8080/contactMessages/getByIdParam?contactMessageId=1  + GET
    public ResponseEntity<ContactMessage> getById(@RequestParam(value = "contactMessageId") Long contactMessageId) {
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }

  /*  @GetMapping("/getByIdWithParam")
    public ResponseMessage<ContactMessageResponse>getMessagebyIdWithParam(@RequestParam("/{id}") Long id){
        ContactMessageResponse response  = contactMessageService.findById(id);
        return ResponseMessage.<ContactMessageResponse>builder()
              .message("Contact message retrieved successfully")
              .httpStatus(HttpStatus.OK)
              .object(response)
              .build();
    }*/

    // Not: ************************************ getByIdWithPath ***************************************

    @GetMapping("/getById/{contactMessageId}")//http://localhost:8080/contactMessages/getById/1  + GET
    public ResponseEntity<ContactMessage> getByIdPath(@PathVariable Long contactMessageId) {
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }


/*@GetMapping("/getByIdWithPath/{id}")
    public ResponseMessage<ContactMessageResponse> getByIdWithPAth(@PathVariable("id")Long id)
{
    ContactMessageResponse byIdResponse = contactMessageService.findById(id);
    return ResponseMessage.<ContactMessageResponse>builder()
            .message("Contact message retrieved successfully")
            .httpStatus(HttpStatus.OK)
            .object(byIdResponse)
            .build();
}*/

}
