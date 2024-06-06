package com.project.repository.business;

import com.project.entity.concretes.business.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepsitory extends JpaRepository<StudentInfo, Long> {
}
