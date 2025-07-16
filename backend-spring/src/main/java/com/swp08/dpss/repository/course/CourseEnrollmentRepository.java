package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.course.CourseEnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, CourseEnrollmentId> {
    CourseEnrollment findByCourse_IdAndUser_Id(Long courseId, Long userId);

}
