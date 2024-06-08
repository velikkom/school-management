package com.project.repository.business;

import com.project.entity.concretes.business.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.DoubleStream;

public interface MeetRepository extends JpaRepository<Meet, Long>
{

    List<Meet> getByAdvisoryTeacher_IdEquals(Long userId);

    List<Meet> findByStudentList_IdEquals(Long userId);

    Page<Meet> findByAdvisoryTeacher_IdEquals(Long id, Pageable pageable);
}
