package com.project.contactmessage.controller;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/contactMessages")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    /**
     * http://localhost:8080/contactMessage/save + Json +Post
     */
    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> saveContact(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
       return contactMessageService.save(contactMessageRequest);
    }

     //
/**
 * Not: ******************************************** getAllByPage ***************************************
 * http://localhost:8080/contactMessage/getAll
 */

    // Not: ************************************* searchByEmailByPage ***************************************

    // Not: *************************************** searchBySubjectByPage ***************************************

    // Not: **************************************searchByDateBetween ***************************************

    // Not:****************************************searchByTimeBetween ***************************************

    // Not: *********************************** deleteByIdParam ***************************************

    // Not: ***************************************** deleteById ***************************************

    // Not: *********************************** getByIdWithParam ***************************************

    // Not: ************************************ getByIdWithPath ***************************************




}
