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
    //to do lesson programme will be added


}
