package com.project.controller.business;

import com.project.payload.request.business.StudentInfoRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.StudentInfoResponse;
import com.project.service.business.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    //http://localhost:8080/studentInfo/save
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/save")

    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                @RequestBody @Valid
                                                                StudentInfoRequest studentInfoRequest)
    {
        return studentInfoService.saveStudentInfo(httpServletRequest,studentInfoRequest);
    }

    //deleteById()
    // http://localhost:8080/studentInfo/deleteById/{id}
    @PreAuthorize("hasAuthority('ADMIN','TEACHER')")
    @DeleteMapping("/delete/{studentInfoId}")
    public ResponseMessage studentInfoDelete(@PathVariable Long id)
    {
       return studentInfoService.deleteStudentInfo(id);
    }

    //getAllWithPage()
    //http




    //update()

    //a teacher wants to get students ınfo who are his students
   // http://localhost:8080/studentInfo/getAllForTeacher
   /*
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllForTeacher")
    public ResponseEntity<Page<StudentInfoRequest>> getAllForTeacher (
            HttpServletRequest httpServletRequest,
             @RequestParam(value = "page") int page,
             @RequestParam(value = "size") int size
            )
    {
        return new RequestEntity<>(StudentInfoService.getAllForTeacher(httpServletRequest, page,size), HttpStatus.OK) ;
    }*/

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllForTeacher")   //
    public ResponseEntity<Page<StudentInfoResponse>> getAllForTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    )
    {
        return new ResponseEntity<>(studentInfoService.getAllForTeacher(httpServletRequest,page,size), HttpStatus.OK);
    }

    //a student want to get his own infos
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/getAllForSTUDENTS")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForStudents(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    )
    {
        return  new ResponseEntity<>(studentInfoService.getAllForStudent
                (httpServletRequest,page,size),HttpStatus.OK);
    }







}
