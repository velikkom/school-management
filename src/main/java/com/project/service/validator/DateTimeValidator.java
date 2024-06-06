package com.project.service.validator;


import com.project.exception.BadRequestexception;
import com.project.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DateTimeValidator {

    public boolean checkTime(LocalTime start, LocalTime stop){
        return start.isAfter(stop) || start.equals(stop) ;
    }

    public void checkTimeWithException(LocalTime start, LocalTime stop){
        if(checkTime(start,stop)){
            throw new BadRequestexception(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
    }
}