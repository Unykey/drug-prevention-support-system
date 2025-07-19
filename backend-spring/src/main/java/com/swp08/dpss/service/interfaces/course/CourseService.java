package com.swp08.dpss.service.interfaces.course;

import com.swp08.dpss.dto.requests.course.*;
import com.swp08.dpss.dto.responses.course.CourseEnrollmentResponse;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.course.LessonProgressResponse;
import com.swp08.dpss.entity.course.*;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseRequest request);

    List<Course> searchCourses(String keyword, List<String> targetGroups)

    List<Course> searchCoursesByName(String keyword);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course updateCourse(Long id, CourseRequest updated);

    void softDeleteCourse(Long id);

    void hardDeleteCourse(Long id);

    void unenrollUser(Long courseId, Long userId);

    CourseLessonResponse addLessonToCourse(Long courseId, CourseLessonRequest lesson);

    List<CourseLesson> getLessonsByCourse(Long courseId);

    CourseLessonResponse getLesson(Long lessonId);

    CourseLessonResponse updateLesson(Long lessonId, CourseLessonRequest updated);

    void deleteLesson(Long lessonId);

    CourseEnrollmentResponse enrollUser(Long courseId, Long userId);

    CourseEnrollmentResponse enroll(CourseEnrollmentRequest request);

    List<CourseEnrollment> getEnrollmentsByCourse(Long courseId);

    LessonProgressResponse addLessonProgress(LessonProgressRequest progress);

    LessonProgressResponse updateLessonProgress(Long progressId, LessonProgressRequest progress);

    List<CourseLessonProgress> getProgressByEnrollment(CourseEnrollmentId enrollmentId);

    void addSurveyToCourse(long id, CourseSurveyRequest request);

    ;
}
