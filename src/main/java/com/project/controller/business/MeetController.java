package com.project.controller.business;

import com.project.payload.response.business.MeetResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetController
{

    private final MeetService meetService;


    //save()

    //update


    //getAll
    //http://localhost:8080/getAll
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<MeetResponse> getAll()
    {
      return meetService.getAll();
    }

    //getById()
    //http://localhost:8080/getById()

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getMeetById/{meetId}")
    public ResponseMessage<MeetResponse> getMeetById(@PathVariable Long id)
    {
      return meetService.getMeetById(id);
    }




}
