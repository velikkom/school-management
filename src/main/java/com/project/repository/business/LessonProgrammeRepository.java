package com.project.repository.business;

import com.project.entity.concretes.business.LessonProgram;
import com.project.payload.response.business.LessonProgramResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface LessonProgrammeRepository extends JpaRepository<LessonProgram, Long> {
    List<LessonProgram> findByUsers_IdNotNull();


    List<LessonProgram> findByUsers_IdNull();

    @Query( "SELECT l FROM LessonProgram  l INNER JOIN l.users users WHERE user.userName =?1")
    Set<LessonProgram> getLessonProgramByUsersUserName(String username);
}

