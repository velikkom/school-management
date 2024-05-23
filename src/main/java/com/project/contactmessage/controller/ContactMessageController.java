package com.project.contactmessage.controller;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/getAllByPage")
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
    @GetMapping("/searchByEmailByPage")
    public ResponseMessage<Page<ContactMessageResponse>> searchByEmailByPage(
            @RequestParam("email") String email,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
        return contactMessageService.searchByEmailByPage(email, pageRequest);
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
    @GetMapping("/searchBySubjectByPage")
    public ResponseMessage<Page<ContactMessageResponse>> searchBySubjectByPage(
            @RequestParam("subject") String subject,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") Sort.Direction direction) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, prop));
        return contactMessageService.searchBySubjectByPage(subject, pageRequest);
    }
    // Not: **************************************searchByDateBetween ***************************************
    /**
     * http://localhost:8080/contactMessage/searchByDateBetween?startDate= + startDate + &endDate= + endDate
     */
    @GetMapping("/searchByDateBetween")
    public ResponseMessage<List<ContactMessageResponse>> searchByDateBetween(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return contactMessageService.searchByDateBetween(startDate, endDate);
    }

    // Not:****************************************searchByTimeBetween ***************************************
    /**
     *http://localhost:8080/contactMessage/searchByTimeBetween
     */

   // @GetMapping("/")
    // Not: *********************************** deleteByIdParam ***************************************
    /**
     * http://localhost:8080/contactMessage/deleteByIdParam
     */
    @DeleteMapping("/deleteByIdParam")
    public ResponseMessage<String> deleteByIdParam(@RequestParam("id") Long id){
        contactMessageService.deleteById(id);
        return ResponseMessage.<String>builder()
                .message("Contact message deleted successfully")
                .httpStatus(HttpStatus.OK)
                .object("Contact message with ID " + id + " deleted.")
                .build();
    }

    // Not: ***************************************** deleteById ***************************************
    @DeleteMapping("/deleteById/{id}")
    public ResponseMessage<String>deleteById(@PathVariable("id")Long id){
        contactMessageService.deleteById(id);
        return ResponseMessage.<String>builder()
                .message("Contact message deleted successfully")
                .httpStatus(HttpStatus.OK)
                .object("Contact message with ID " + id + " deleted")
                .build();
    }

    // Not: *********************************** getByIdWithParam ***************************************

    @GetMapping("/getByIdWithParam")
    public ResponseMessage<ContactMessageResponse>getMessagebyIdWithParam(@RequestParam("/{id}") Long id){
        ContactMessageResponse response  = contactMessageService.findById(id);
        return ResponseMessage.<ContactMessageResponse>builder()
              .message("Contact message retrieved successfully")
              .httpStatus(HttpStatus.OK)
              .object(response)
              .build();
    }

    // Not: ************************************ getByIdWithPath ***************************************
@GetMapping("/getByIdWithPath/{id}")
    public ResponseMessage<ContactMessageResponse> getByIdWithPAth(@PathVariable("id")Long id)
{
    ContactMessageResponse byIdResponse = contactMessageService.findById(id);
    return ResponseMessage.<ContactMessageResponse>builder()
            .message("Contact message retrieved successfully")
            .httpStatus(HttpStatus.OK)
            .object(byIdResponse)
            .build();
}

}
