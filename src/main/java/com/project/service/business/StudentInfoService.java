package com.project.service.business;

import com.project.entity.concretes.business.EducationTerm;
import com.project.entity.concretes.business.Lesson;
import com.project.entity.concretes.user.User;
import com.project.entity.enums.Note;
import com.project.entity.enums.RoleType;
import com.project.payload.request.business.StudentInfoRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.StudentInfoResponse;
import com.project.repository.business.StudentInfoRepsitory;
import com.project.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class StudentInfoService
{
   private final StudentInfoRepsitory studentInfoRepository;
   private final MethodHelper methodHelper;
   private final LessonService lessonService;
   private final EducationTermServie educationTermServie;

   @Value("${midterm.exam.percentage}")
   private Double midtermExamPercentage;

   @Value("${final.exam.percentage}")
   private Double finalExamPercentage;


   public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                               StudentInfoRequest studentInfoRequest)
   {
      String teacherUsername = (String) httpServletRequest.getAttribute("username");

      //getting student by ıd from request
      User student = methodHelper.isUserExist(studentInfoRequest.getStudentId());

      // checking role student which is coming from request role is student that we reach below
      methodHelper.checkRole(student, RoleType.STUDENT);

      //with username reaching Teacher
      User teacher = methodHelper.isUserExistByUsername(teacherUsername);

      Lesson lesson = lessonService.isLessonExistsById(studentInfoRequest.getStudentId());

      EducationTerm educationTerm = educationTermServie.findEducationTermById(studentInfoRequest.getEducationTermId());

      //letter grade calculate
   Note note  = checkLetterGrade(calculateAverageExam(studentInfoRequest.getMidtermExam(),
                                                      studentInfoRequest.getMidtermExam()));

      //Dto-Pojo
      //TODO 01:09 DAY 9
      //Pojo-Dto
      return null;
   }

   private Double calculateAverageExam(Double midTerExam,
                                       Double finalExam)
   {
      return (midTerExam * midtermExamPercentage) + (finalExam * finalExamPercentage);
   }


   private Note checkLetterGrade(Double average)
   {
      if(average<50.0){
         return Note.FF;
      } else if (average<60) {
         return Note.DD;
      } else if (average<65) {
         return Note.CC;
      } else if (average<70) {
         return Note.CB;
      } else if (average<75) {
         return Note.BB;
      } else if (average<80) {
         return Note.BA;
      } else   {
         return Note.AA;
      }
   }
}
