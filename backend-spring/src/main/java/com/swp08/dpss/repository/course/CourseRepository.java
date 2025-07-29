package com.swp08.dpss.repository.course;

import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.enums.TargetGroupName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllByTitleContainingIgnoreCase(String title);

    List<Course> findAllByTitleContainingIgnoreCaseAndTargetGroupsIn(String keyword, List<String> targetGroups);

    List<Course> findAllByTargetGroupsIn(List<String> targetGroups);

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.lessons JOIN FETCH c.targetGroups")
    List<Course> findAllWithLessonsAndTargetGroups();

    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.lessons " +
            "LEFT JOIN FETCH c.targetGroups tg " + // Join target groups
            "LEFT JOIN FETCH c.enrollments " +
            "LEFT JOIN FETCH c.courseSurveyList " +
            "WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND tg.targetGroupName IN :targetGroupNames")
    List<Course> findAllByTitleContainingIgnoreCaseAndTargetGroups_TargetGroupNameIn(String keyword, Set<TargetGroupName> targetGroupNames);

    // New query to find by target group names directly (using enum)
    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.lessons " +
            "LEFT JOIN FETCH c.targetGroups tg " + // Join target groups
            "LEFT JOIN FETCH c.enrollments " +
            "LEFT JOIN FETCH c.courseSurveyList " +
            "WHERE tg.targetGroupName IN :targetGroupNames")
    List<Course> findAllByTargetGroups_TargetGroupNameIn(Set<TargetGroupName> targetGroupNames);
    // --- End new search methods ---

    // --- ENSURE THIS METHOD IS PRESENT AND CORRECT ---
    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.lessons " +
            "LEFT JOIN FETCH c.targetGroups " +
            "LEFT JOIN FETCH c.enrollments " +
            "LEFT JOIN FETCH c.courseSurveyList")
    List<Course> findAllCoursesWithAllDetails();

}
