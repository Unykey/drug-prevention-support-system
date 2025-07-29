package com.swp08.dpss.service.interfaces.course;

import com.swp08.dpss.dto.requests.course.*;
import com.swp08.dpss.dto.responses.course.CourseEnrollmentResponse;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.course.CourseResponse;
import com.swp08.dpss.dto.responses.course.LessonProgressResponse;
import com.swp08.dpss.entity.course.*;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);

    List<CourseResponse> searchCourses(String keyword, List<String> targetGroups);

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(Long id);

    CourseResponse updateCourse(Long id, CourseRequest updated);

    void softDeleteCourse(Long id);

    void hardDeleteCourse(Long id);

    void unenrollUser(Long courseId, Long userId);

    CourseLessonResponse addLessonToCourse(Long courseId, CourseLessonRequest lesson);

    List<CourseLessonResponse> getLessonsByCourse(Long courseId);

    CourseLessonResponse getLesson(Long lessonId);

    CourseLessonResponse updateLesson(Long lessonId, CourseLessonRequest updated);

    void deleteLesson(Long lessonId);

    CourseEnrollmentResponse enrollUser(Long courseId, Long userId);

    CourseEnrollmentResponse enroll(CourseEnrollmentRequest request);

    List<CourseEnrollmentResponse> getEnrollmentsByCourse(Long courseId);

    LessonProgressResponse addLessonProgress(LessonProgressRequest progress);

    LessonProgressResponse updateLessonProgress(Long progressId, LessonProgressRequest progress);

    List<LessonProgressResponse> getProgressByEnrollment(CourseEnrollmentId enrollmentId);

    void addSurveyToCourse(long id, CourseSurveyRequest request);


}
