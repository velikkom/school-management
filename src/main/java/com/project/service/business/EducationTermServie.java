package com.project.service.business;

import com.project.entity.concretes.business.EducationTerm;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.business.EducationTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermServie
{
    private final EducationTermRepository educationTermRepository;


    public EducationTermResponse getEducationTermById(Long id)
    {
        EducationTerm educationTerm = isEducationTermExist(id);
        //todo term response
        return null;
    }

    private EducationTerm isEducationTermExist(Long id)
    {
        return educationTermRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE)));
    }
}
