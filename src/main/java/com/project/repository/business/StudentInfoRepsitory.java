package com.project.repository.business;

import com.project.entity.concretes.business.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.DoubleStream;

public interface StudentInfoRepsitory extends JpaRepository<StudentInfo, Long> {

    @Query("SELECT s FROM StudentInfo s WHERE s.teacher.username= ?1")
    Page<StudentInfo> findByTeacherId_UsernameEquals(String username, Pageable pageable);

    @Query("SELECT s FROM StudentInfo s WHERE s.teacher.username= ?1")
    Page<StudentInfo> findByStudentId_UsernameEquals(String username, Pageable pageable);


    boolean existsByIdEquals(Long id);
}
