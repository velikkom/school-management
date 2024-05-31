package com.project.service.user;

import com.project.entity.concretes.user.User;
import com.project.entity.enums.RoleType;
import com.project.payload.mappers.UserMapper;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.user.TeacherRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.payload.response.user.TeacherResponse;
import com.project.payload.response.user.UserResponse;
import com.project.repository.user.UserRepository;
import com.project.service.helper.MethodHelper;
import com.project.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService
{
    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final MethodHelper methodHelper;



    public ResponseMessage<TeacherResponse> saveTeacher(TeacherRequest teacherRequest)
    {
        //todo will be added lesson programme


        //is unique or not
        uniquePropertyValidator.checkDublicate(teacherRequest.getUsername(), teacherRequest.getSsn(), teacherRequest.getPhoneNumber(), teacherRequest.getEmail());

        //dto to pojo

        User teacher = userMapper.mapTeacherRequestToUser(teacherRequest);
            teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        //to do lesson programme will be added

        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        if (teacherRequest.getIsAdvisorTeacher())
        {
            teacher.setIsAdvisor(Boolean.TRUE);
        }else teacher.setIsAdvisor(Boolean.FALSE);
        User savedTeacher = userRepository.save(teacher);

        return ResponseMessage.<TeacherResponse>
        builder()
                .message(SuccessMessages.TEACHER_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(userMapper.mapUserToTeacherResponse(savedTeacher))
                .build();

    }

    public List<StudentResponse> getAllStudentByAdvisorUsername(String username)
    {
        User teacher = methodHelper.isUserExistByUsername(username);
        methodHelper.checkadvisor(teacher);

        return userRepository.findByAdvisorTeacherId(teacher.getId())
                .stream()
                .map(userMapper::mapUserToStudentResponse)
                .collect(Collectors.toList());
    }


    public List<UserResponse> getAllAdvisorTeacher()
    {
        return userRepository.findAllByAdvisor(Boolean.TRUE)
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<TeacherResponse> updateTeacherById(Long id, TeacherRequest teacherRequest)
    {
        User existingTeacher = methodHelper.isUserExist(id);
        uniquePropertyValidator.checkUniqueProperties(existingTeacher,teacherRequest);

        User updatedTeacher = userMapper.mapTeacherRequestToUser(teacherRequest);
        updatedTeacher.setId(id);
        updatedTeacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        updatedTeacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
        updatedTeacher.setIsAdvisor(teacherRequest.getIsAdvisorTeacher());
        User savedTeacher = userRepository.save(updatedTeacher);


        return ResponseMessage.<TeacherResponse>
                builder()
                .message(SuccessMessages.TEACHER_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToTeacherResponse(savedTeacher))
                .build();
    }

    public ResponseMessage<String> deleteAdvisorTeacherById(Long id)
    {
        User teacher = methodHelper.isUserExist(id);
        methodHelper.checkadvisor(teacher);

        userRepository.deleteById(id);

        return ResponseMessage.<String>builder()
                .message(SuccessMessages.TEACHER_DELETE)
                .httpStatus(HttpStatus.OK)
                .object(SuccessMessages.TEACHER_DELETE)
                .build();
    }

    public ResponseMessage<TeacherResponse> saveAdvisorTeacherByTeacherId(Long id)
    {
        User teacher = methodHelper.isUserExist(id);

        teacher.setIsAdvisor(Boolean.TRUE);
        User updatedTeacher = userRepository.save(teacher);

        return ResponseMessage.<TeacherResponse>builder()
                .message(SuccessMessages.TEACHER_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToTeacherResponse(updatedTeacher))
                .build();
    }


    //to do lesson programme will be added


}
