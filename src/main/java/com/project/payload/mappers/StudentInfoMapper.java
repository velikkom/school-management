package com.project.payload.mappers;

import com.project.entity.concretes.business.StudentInfo;
import com.project.entity.enums.Note;
import com.project.payload.request.business.StudentInfoRequest;
import org.springframework.stereotype.Component;

@Component
public class StudentInfoMapper
{

    public StudentInfo mapStudentInfoRequestToStudentInfo(StudentInfoRequest studentInfoRequest,
                                                          Note note,
                                                          Double average)

    {
        return StudentInfo.builder()
                .infoNote(studentInfoRequest.getInfoNote())
                .absentee(studentInfoRequest.getAbsentee())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam())
                .examAverage(average)
                .letterGrade(note)
                .build();
    }


}
