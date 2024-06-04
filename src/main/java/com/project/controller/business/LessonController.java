package com.project.controller.business;

import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.business.LessonResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    //http://localhost:8080/lessons/save
    //save

    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PostMapping("/save")
    public ResponseMessage<LessonResponse> save(@RequestBody @Valid
                                                LessonRequest lessonRequest)
    {
        return lessonService.saveLesson(lessonRequest);
    }
    //http://localhost:8080/lessons/getLessonByName?lessonName=Java

    @GetMapping("/getLessonByName")
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER'")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName)
    {
       return lessonService.getLessonByLessonName(lessonName);
    }

    ////http://localhost:8080/lessons/update/1
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER'")
    @PutMapping("/update/{lessonId}")
    public ResponseEntity<LessonResponse> updateLessonById(
            @PathVariable Long lessonId,
            @RequestBody LessonRequest lessonRequest)
    {
        return ResponseEntity.ok(lessonService.updateLessonById(lessonId,lessonRequest));
    }



//todo homework lesson
    //deleteById admın
    //http://8080:localhost/http://localhost:8080/lessons/


    //getAllWithPage

    //getLessonsByIdList @requestParam


}
