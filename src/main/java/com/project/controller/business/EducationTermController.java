package com.project.controller.business;

import com.project.service.business.EducationTermServie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/educationTerms")
@RequiredArgsConstructor
public class EducationTermController
{
    private final EducationTermServie educationTermServie;

    //** save
    //TODO SAVE


    //http://localhost:8080/educationTerms/1 +get
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public EducationTermResponse getEducationTermById(@PathVariable Long id)
    {
        return educationTermServie.getEducationTermById(id);
    }
}
