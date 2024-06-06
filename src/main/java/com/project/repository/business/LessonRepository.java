package com.project.repository.business;

import com.project.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.LongStream;

public interface LessonRepository extends JpaRepository<Lesson, Long>
{


    boolean existsByLessonByLessonNameEqualsIgnoreCase(String lessonName);


    Optional<Lesson> getLessonByLessonName(String lessonName);



}
