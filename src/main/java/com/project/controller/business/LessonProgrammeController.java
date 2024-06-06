package com.project.controller.business;

import com.project.payload.request.business.LessonProgramRequest;
import com.project.payload.response.business.LessonProgramResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.LessonProgrammeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgrammeController
{
    private final LessonProgrammeService lessonProgrammeService;


    //save
    //http://localhost:8080/lessonPrograms/save
    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@RequestBody @Valid
                                                                        LessonProgramRequest lessonProgramRequest)
    {
        return lessonProgrammeService.saveLessonProgram(lessonProgramRequest);
    }

    //http://localhost:8080/lessonPrograms/getAll
    @PostMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllLessonPrograms()
    {
        return lessonProgrammeService.getAllLessonPrograms();
    }

    //getById
    //http://localhost:8080/lessonPrograms/getById
    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public LessonProgramResponse getLessonProgramById(@PathVariable  Long id  )
    {
        return lessonProgrammeService.getLessonProgramById(id);
    }

    //getAllUnAssignedLessonPrograms
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    @GetMapping("/getAllUnassignedLessonPrograms")
    public List<LessonProgramResponse> getAllUnAssigned ()
    {
        return lessonProgrammeService.getAllUnAssigned();
    }

    //getAllAssignedLessonPrograms
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    @GetMapping("/getAllAssignedLessonPrograms")
    public List<LessonProgramResponse> getAllAssigned ()
    {
        return lessonProgrammeService.getAllAssigned();
    }


    //delete()
    //http://localhost:8080/lessonPrograms/delete/{id}
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> deleteLessonProgram(@PathVariable Long id)
    {
        return lessonProgrammeService.deleteLessonProgram(id);
    }

    //getAllWithPage
    //http://localhost:8080/lessonPrograms/getAllWithPage?page=0&size=2
    @GetMapping("/getAllWithPage")
    @PreAuthorize("hasRole('ROLE_ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public Page<LessonProgramResponse> getAllLessonProgramsByPAge(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "direction", defaultValue = "Desc") String direction)
    {
        return  lessonProgrammeService.getAllLessonProgramByPage(page,size,sort,direction);
    }

    //teacher getAllLessonPrograms
    //http://localhost:8080/lessonPrograms/getAllLessonProgramsByTeacher
    @GetMapping("/getAllLessonProgramsByTeacher")
    @PreAuthorize("hasRole('TEACHER')")
    public Set<LessonProgramResponse> getAllLessonProgramsByTeacher(HttpServletRequest request)
    {
        return lessonProgrammeService.getAllLessonProgramsByUser(request);
    }

    //student getAllLessonPrograms
    //http://localhost:8080/lessonPrograms/getAllLessonProgramsByStudent
    @GetMapping("/getAllLessonProgramsByStudent")
    @PreAuthorize("hasRole('STUDENT')")
    public Set<LessonProgramResponse> getAllLessonProgramsByStudent(HttpServletRequest request)
    {
        return lessonProgrammeService.getAllLessonProgramsByUser(request);
    }



    





}
