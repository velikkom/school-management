package com.project.service.business;

import com.project.entity.concretes.business.EducationTerm;
import com.project.entity.concretes.business.Lesson;
import com.project.entity.concretes.business.StudentInfo;
import com.project.entity.concretes.user.User;
import com.project.entity.enums.Note;
import com.project.entity.enums.RoleType;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.StudentInfoMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.StudentInfoRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.StudentInfoResponse;
import com.project.repository.business.StudentInfoRepsitory;
import com.project.service.helper.MethodHelper;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
   private final StudentInfoMapper studentInfoMapper;
   private final PageableHelper pageableHelper;



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
    StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
              studentInfoRequest,
              note,
              calculateAverageExam(studentInfoRequest.getMidtermExam(),studentInfoRequest.getFinalExam()));

     studentInfo.setStudent(student);
     studentInfo.setTeacher(teacher);
     studentInfo.setEducationTerm(educationTerm);
     studentInfo.setLesson(lesson);

      StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
      return ResponseMessage.<StudentInfoResponse>builder()
              .message(SuccessMessages.STUDENT_INFO_SAVE)
              .object(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
              .httpStatus(HttpStatus.CREATED)
              .build();


      //TODO 01:19 DAY 9
      //Pojo-Dto

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
   public  Page<StudentInfoResponse> getAllForTeacher(HttpServletRequest httpServletRequest, int page, int size)
   {

      Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

      String username = (String) httpServletRequest.getAttribute("username");

      return studentInfoRepository.findByTeacherId_UsernameEquals(username, pageable)
              .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);
   }

   public Page<StudentInfoResponse> getAllForStudent(HttpServletRequest httpServletRequest, int page, int size)
   {
      Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

      String username = (String) httpServletRequest.getAttribute("username");

      return studentInfoRepository.findByStudentId_UsernameEquals(username, pageable)
              .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);
   }

   public ResponseMessage deleteStudentInfo(Long id)
   {
      StudentInfo studentInfo = isStudentInfoExistById(id);
      studentInfoRepository.deleteById(studentInfo.getId());
      return ResponseMessage.builder()
              .message(SuccessMessages.STUDENT_INFO_DELETE)
              .httpStatus(HttpStatus.OK)
              .build();

   }

   private StudentInfo isStudentInfoExistById(Long id)
   {
      boolean isExist = studentInfoRepository.existsByIdEquals(id);

      if (!isExist)
      {
         throw new ResourceNotFoundException(String.format(ErrorMessages.STUDENT_INFO_NOT_FOUND,id));
      }else{
         return studentInfoRepository.findById(id).get();
      }


   }


   public Page<StudentInfoResponse> getAllStudentInfoByPAge(int page, int size, String sort, String  type)
   {
      Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
      return studentInfoRepository.findAll(pageable)
              .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);
   }
}
