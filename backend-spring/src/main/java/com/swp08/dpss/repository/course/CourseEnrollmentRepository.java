package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment,Long> {
}
