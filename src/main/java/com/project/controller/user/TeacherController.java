package com.project.controller.user;

import com.project.payload.request.user.TeacherRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.payload.response.user.TeacherResponse;
import com.project.payload.response.user.UserResponse;
import com.project.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController
{
    private final TeacherService teacherService;

    // http://localhost:8080/teacher/save + JSON + POST
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<TeacherResponse>> saveTeacher (@RequestBody @Valid TeacherRequest teacherRequest)
    {
        return ResponseEntity.ok(teacherService.saveTeacher(teacherRequest));
    }

    // Not:  updateTeacherById() ***************************************************


    // Not: SaveAdvisorTeacherByTeacherId() ****************************************


    // Not :  deleteAdvisorTeacherById() *******************************************

    // Not: GetAllStudentByAdvisorUserName()
    // // http://localhost:8080/teacher/getAllAdvisorTeacher
    // **********************************************
    // !!! Bir rehber ogretmenin kendi ogrencilerin tamamini getiren metod

    @GetMapping("/GetAllStudentByAdvisorUserName")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<StudentResponse> getAllStudentByAdvisorUsername(HttpServletRequest request)
    {
        String username = request.getHeader("username");
        return teacherService.getAllStudentByAdvisorUsername(username);
    }
   // http://localhost:8080/teacher/getAllAdvisorTeacher
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllAdvisorTeacher") //
    public List<UserResponse> getAllAdvisorTeacher()
    {
        return teacherService.getAllAdvisorTeacher();
    }


}
