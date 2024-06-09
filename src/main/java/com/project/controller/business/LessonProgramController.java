package com.project.controller.business;

import com.project.payload.request.business.LessonProgramRequest;
import com.project.payload.response.business.LessonProgramResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.LessonProgramService;
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
public class LessonProgramController
{
    private final LessonProgramService lessonProgramService;


    //save
    //http://localhost:8080/lessonPrograms/save
    @PostMapping("/save") // http://localhost:8080/lessonPrograms/save + POST + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@RequestBody @Valid
                                                                    LessonProgramRequest lessonProgramRequest){
        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }

    //http://localhost:8080/lessonPrograms/getAll
    @PostMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAllLessonPrograms()
    {
        return lessonProgramService.getAllLessonPrograms();
    }

    //getById
    //http://localhost:8080/lessonPrograms/getById
    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public LessonProgramResponse getLessonProgramById(@PathVariable  Long id  )
    {
        return lessonProgramService.getLessonProgramById(id);
    }

    //getAllUnAssignedLessonPrograms
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    @GetMapping("/getAllUnassignedLessonPrograms")
    public List<LessonProgramResponse> getAllUnAssigned ()
    {
        return lessonProgramService.getAllUnAssigned();
    }

    //getAllAssignedLessonPrograms
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    @GetMapping("/getAllAssignedLessonPrograms")
    public List<LessonProgramResponse> getAllAssigned ()
    {
        return lessonProgramService.getAllAssigned();
    }


    //delete()
    //http://localhost:8080/lessonPrograms/delete/{id}
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> deleteLessonProgram(@PathVariable Long id)
    {
        return lessonProgramService.deleteLessonProgram(id);
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
        return  lessonProgramService.getAllLessonProgramByPage(page,size,sort,direction);
    }

    //teacher getAllLessonPrograms
    //http://localhost:8080/lessonPrograms/getAllLessonProgramsByTeacher
    @GetMapping("/getAllLessonProgramsByTeacher")
    @PreAuthorize("hasRole('TEACHER')")
    public Set<LessonProgramResponse> getAllLessonProgramsByTeacher(HttpServletRequest request)
    {
        return lessonProgramService.getAllLessonProgramsByUser(request);
    }

    //student getAllLessonPrograms
    //http://localhost:8080/lessonPrograms/getAllLessonProgramsByStudent
    @GetMapping("/getAllLessonProgramsByStudent")
    @PreAuthorize("hasRole('STUDENT')")
    public Set<LessonProgramResponse> getAllLessonProgramsByStudent(HttpServletRequest request)
    {
        return lessonProgramService.getAllLessonProgramsByUser(request);
    }



    





}
