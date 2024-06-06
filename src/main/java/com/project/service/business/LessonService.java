package com.project.service.business;

import com.project.entity.concretes.business.Lesson;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.LessonMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.business.LessonResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.LessonRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final PageableHelper pageableHelper;


    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {
        isLessonExistByLessonName(lessonRequest.getLessonName());
        Lesson savedLesson = lessonRepository.save(lessonMapper.mapLessonRequestToLesson(lessonRequest));
        return ResponseMessage.<LessonResponse>
                        builder()
                .object(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .message(SuccessMessages.LESSON_SAVE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private boolean isLessonExistByLessonName(String lessonName) {
        boolean lessonExist = lessonRepository.existsByLessonByLessonNameEqualsIgnoreCase(lessonName);


        if (lessonExist) {
            throw new ConflictException(String.format(ErrorMessages.LESSON_ALREADY_EXIST_WITH_LESSON_NAME));
        } else return false;

    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {
        if (lessonRepository.getLessonByLessonName(lessonName).isPresent()) {
            return ResponseMessage.<LessonResponse>builder()
                    .message(SuccessMessages.LESSON_FOUND)
                    .object(lessonMapper.mapLessonToLessonResponse(lessonRepository.getLessonByLessonName(lessonName).get())).build();
        } else {
            return ResponseMessage.<LessonResponse>builder()
                    .message(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonName))
                    .build();
        }
    }


    public LessonResponse updateLessonById(Long lessonId, LessonRequest lessonRequest)
    {
        Lesson lesson = isLessonExistsById(lessonId);

        if (!(lesson.getLessonName().equals(lessonRequest.getLessonName())
                &&
                lessonRepository.existsByLessonByLessonNameEqualsIgnoreCase(lessonRequest.getLessonName())))
        {
            throw new ConflictException(String.format(ErrorMessages.LESSON_ALREADY_EXIST_WITH_LESSON_NAME, lessonRequest.getLessonName()));
        }


        Lesson updatedLesson = lessonMapper.mapLessonRequestToUpdatedLesson(lessonId, lessonRequest);

        updatedLesson.setLessonPrograms(lesson.getLessonPrograms());

        return lessonMapper.mapLessonToLessonResponse(lessonRepository.save(updatedLesson));

    }

    public Lesson isLessonExistsById(Long id)
    {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, id)));
    }


    public ResponseMessage deleteLessonById(Long id)
    {
        isLessonExistsById(id);
        lessonRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.LESSON_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public Page<LessonResponse> getAllWithPage(int page, int size, String sort, String direction)
    {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, direction);
        return lessonRepository.findAll(pageable)
                .map(lessonMapper::mapLessonToLessonResponse);
    }


    public Set<Lesson> getLessonsByIdSet(Set<Long> lessonId)
    {
        return lessonId.stream()
                .map(this::isLessonExistsById)
               // .map(lessonMapper::mapLessonToLessonResponse)
                .collect(Collectors.toSet());
    }
}




























