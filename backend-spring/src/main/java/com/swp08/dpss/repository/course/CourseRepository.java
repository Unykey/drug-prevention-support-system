package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllByTitleContainingIgnoreCase(String title);

    List<Course> findAllByTitleContainingIgnoreCaseAndTargetGroupsIn(String keyword, List<String> targetGroups);

    List<Course> findAllByTargetGroupsIn(List<String> targetGroups);
}
