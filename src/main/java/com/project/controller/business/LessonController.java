package com.project.controller.business;

import com.project.entity.concretes.business.Lesson;
import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.business.LessonResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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
    //http://localhost:8080/lessons/{id}
    @PreAuthorize("hasAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseMessage<?> deleteLessonById(@PathVariable Long id)
    {
        return lessonService.deleteLessonById(id);
    }

    //getAllWithPage
    //http://localhost:8080/lessons/getAllWithPage
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllWithPage")
    public Page<LessonResponse> getAllWithPage(
            @RequestParam(value = "page",defaultValue ="0" ) int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "lessonName") String sort,
@RequestParam(value = "direction",defaultValue = "Desc") String direction)
    {
        return lessonService.getAllWithPage(page,size,sort,direction);
    }


    //getLessonsByIdList @requestParam

    //http://localhost:8080/lessons/getLessonsByIdList?lessonId=1,2,3
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getLessonsByIdList")
    public Set<Lesson> getLessonsByIdList(@RequestParam(name = "lessonId") Set<Long> lessonId)
    {
        return lessonService.getLessonsByIdSet(lessonId);
    }
}
