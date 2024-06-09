package com.project.controller.business;

import com.project.payload.request.business.EducationTermRequest;
import com.project.payload.response.business.EducationTermResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educationTerms")
@RequiredArgsConstructor
public class EducationTermController {
    private final EducationTermService educationTermServie;

    //** save
    //TODO SAVE
    //http://localhost:8080/educationTerms/save + json +post
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@RequestBody @Valid EducationTermRequest educationTermRequest) {
        return educationTermServie.saveEducationTerm(educationTermRequest);
    }


    //http://localhost:8080/educationTerms/1 +get
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public EducationTermResponse getEducationTermById(@PathVariable Long id) {
        return educationTermServie.getEducationTermById(id);
    }


    // getAll
    //http://localhost:8080/educationTerms/getAll +get
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @GetMapping("/getAll")
    public List<EducationTermResponse> getAllEducationTerms() {
        return educationTermServie.getAllEducationTerms();
    }

    //getAllWithPage
    //http://localhost:8080/educationTerms/getAllWithPage?page=0&size=2 +get
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @GetMapping("/getAllEducationTermsByPage")
    public Page<EducationTermResponse> getAllEducationTermsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "direction", defaultValue = "Desc") String direction) {
        return educationTermServie.getAllEducationTermsByPage(page, size, sort, direction);
    }


    //deleteById
    //http://localhost:8080/educationTerms/delete/1
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteEducationTermById(@PathVariable Long id)
    {
        return educationTermServie.deleteEducationTermById(id);
    }

    //updateById
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseMessage<EducationTermResponse> updateEducationTerm(@PathVariable Long id,
                                                                      @RequestBody @Valid EducationTermRequest educationTermRequest)
    {
        return educationTermServie.updateEducationTerm(id,educationTermRequest);
    }

}













