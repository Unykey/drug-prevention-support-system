package com.swp08.dpss.service.interfaces.course;

import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.course.CourseLesson;
import com.swp08.dpss.entity.course.LessonProgress;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course createCourse(Course course);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course updateCourse(Long id, Course updated);

    void deleteCourse(Long id);

    CourseLesson addLessonToCourse(Long courseId, CourseLesson lesson);

    List<CourseLesson> getLessonsByCourse(Long courseId);

    CourseLesson updateLesson(Long lessonId, CourseLesson updated);

    void deleteLesson(Long lessonId);

    CourseEnrollment enrollUser(Long courseId, Long userId);

    List<CourseEnrollment> getEnrollmentsByCourse(Long courseId);

    LessonProgress addLessonProgress(LessonProgress progress);

    LessonProgress updateLessonProgress(Long progressId, LessonProgress progress);

    List<LessonProgress> getProgressByEnrollment(Long enrollmentId);
}
