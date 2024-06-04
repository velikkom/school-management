package com.project.controller.user;

import com.project.payload.request.user.StudentRequest;
import com.project.payload.request.user.StudentRequestWithoutPassword;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    //http://localhost:8080/student/save + json +post
    @PostMapping("/save")
    @PreAuthorize("haAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(@RequestBody @Valid StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.saveStudent(studentRequest));
    }

    //http://localhost:8080/student/update
    // Not: updateStudentForStudents() ***********************************************
    // !!! ogrencinin kendisini update etme islemi
    @PatchMapping("/update")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<String>
    updateStudent (@RequestBody @Valid
                   StudentRequestWithoutPassword studentRequestWithoutPassword,
                HttpServletRequest request)
    {
        return studentService.updateStudent (studentRequestWithoutPassword , request);
    }
    // Not: updateStudent() **********************************************************
    //http://localhost:8080/student/update/2 + json +patch
    @PutMapping("/update/{userid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
public ResponseMessage<StudentResponse> updateStudentForManager
    (@PathVariable Long userid,
     @RequestBody @Valid StudentRequest studentRequest)
    {
        return studentService.updateStudentForManager(userid, studentRequest);
    }

    // Not: ChangeActıveStatusOfStudent() ********************************************
    //http://localhost:8080/student/changeStatus?id=1&status=true +get
    @GetMapping("/changeStatus")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage changeActiveStatusOfStudent
    (@RequestParam Long id,
     @RequestParam Boolean status)
    {
        return studentService.changeStatusOfStudent(id, status);
    }


    // TODO : LESSON PROGRAM
}
