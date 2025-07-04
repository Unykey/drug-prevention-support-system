package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
