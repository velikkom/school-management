package com.project.controller.user;

import com.project.payload.request.business.ChooseLessonTeacherRequest;
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

    // Not:  updateTeacherById() ***************************************************+---check again
    //http://localhost:8080/teacher/updateTeacherById

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PutMapping("/update/{userId}")  // http://localhost:8080/user/update/1
    public ResponseMessage<TeacherResponse>updateTeacherForManagers(@RequestBody @Valid TeacherRequest teacherRequest,
                                                                    @PathVariable Long userId){
        return teacherService.updateTeacherForManagers(teacherRequest,userId);
    }

   /* @PutMapping("/updateTeacherById")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<TeacherResponse>>  updateTeacherById(@RequestParam Long id,@RequestBody @Valid TeacherRequest teacherRequest)
    {
        return ResponseEntity.ok(teacherService.updateTeacherById (id,teacherRequest));
    }*/

    // Not: SaveAdvisorTeacherByTeacherId() ****************************************
    //http://localhost:8080/teacher/SaveAdvisorTeacherByTeacherId

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PatchMapping("/saveAdvisorTeacher/{teacherId}") // http://localhost:8080/teacher/saveAdvisorTeacher/1
    public ResponseMessage<UserResponse> saveAdvisorTeacher (@PathVariable Long teacherId){
        return teacherService.saveAdvisorTeacher(teacherId);
    }

   /* @PostMapping("/SaveAdvisorTeacherByTeacherId")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<TeacherResponse>> saveAdvisorTeacherByTeacherId(@RequestParam Long id) {
        return ResponseEntity.ok(teacherService.saveAdvisorTeacherByTeacherId(id));
    }*/

    // Not :  deleteAdvisorTeacherById() *******************************************
    //http://localhost:8080/teacher/deleteAdvisorTeacherById/1
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/deleteAdvisorTeacherById/{id}")
    public ResponseMessage<UserResponse> deleteAdvisorTeacherById(@PathVariable Long id){
        return teacherService.deleteAdvisorTeacherById(id);
    }

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

    //http://localhost:8080/teacher/addLessonProgram
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PostMapping("/addLessonProgram")  //
    public ResponseMessage<TeacherResponse> chooseLesson(@RequestBody @Valid
                                                         ChooseLessonTeacherRequest chooseLessonTeacherRequest){
        return teacherService.addLessonProgram(chooseLessonTeacherRequest);
    }


}
