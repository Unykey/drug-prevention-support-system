package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.CourseEnrollmentId;
import com.swp08.dpss.entity.course.CourseSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSurveyRepository extends JpaRepository<CourseSurvey, CourseEnrollmentId> {
}
