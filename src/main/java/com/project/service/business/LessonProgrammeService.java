package com.project.service.business;

import com.project.entity.concretes.business.EducationTerm;
import com.project.entity.concretes.business.Lesson;
import com.project.entity.concretes.business.LessonProgram;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.LessonProgramMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.LessonProgramRequest;
import com.project.payload.response.business.LessonProgramResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.LessonProgrammeRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonProgrammeService
{
    private final LessonProgrammeRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermServie educationTermService;
    private final DateTimeValidator dateTimeValidator;
    private final LessonProgramMapper lessonProgramMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {

        Set<Lesson> lessons = lessonService.getLessonsByIdSet(lessonProgramRequest.getLessonIdList());

        EducationTerm educationTerm = educationTermService.findEducationTermById(lessonProgramRequest.getEducationTermId());

        if(lessons.isEmpty()){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_IN_LIST);
        }

        dateTimeValidator.checkTimeWithException(lessonProgramRequest.getStartTime(),
                lessonProgramRequest.getStopTime());

        LessonProgram lessonProgram =
                lessonProgramMapper.mapLessonProgramRequestToLessonProgram(lessonProgramRequest,lessons,educationTerm);

        LessonProgram savedLessonProgram =  lessonProgramRepository.save(lessonProgram);

        return ResponseMessage.<LessonProgramResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .build();
    }


    public List<LessonProgramResponse> getAllLessonPrograms()
    {
        return lessonProgramRepository.findAll()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }


    public LessonProgramResponse getLessonProgramById(Long id)
    {
        LessonProgram lessonProgramExist = isLessonProgramExist(id);
        return lessonProgramMapper.mapLessonProgramToLessonProgramResponse(lessonProgramExist);
      //  return lessonProgramMapper.mapLessonProgramToLessonProgramResponse(isLessonProgramExist(id)


    }

    public LessonProgram isLessonProgramExist(Long id) {
        return lessonProgramRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.LESSON_PROGRAM_NOT_FOUND_MESSAGE));
    }


    public List<LessonProgramResponse> getAllUnAssigned()
    {
        return lessonProgramRepository.findByUsers_IdNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllAssigned()
    {
        return lessonProgramRepository.findByUsers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<LessonProgramResponse> deleteLessonProgram(Long id)
    {
        isLessonProgramExist(id);

        lessonProgramRepository.deleteById(id);

        return ResponseMessage.<LessonProgramResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<LessonProgramResponse> getAllLessonProgramByPage(int page, int size, String sort, String direction)
    {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, direction);

        return lessonProgramRepository.findAll(pageable)
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse);

    }

    public Set<LessonProgramResponse> getAllLessonProgramsByUser(HttpServletRequest request)
    {
        String username = (String) request.getAttribute("username");

        return lessonProgramRepository.getLessonProgramByUsersUserName(username)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
    }














}
