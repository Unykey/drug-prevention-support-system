package com.swp08.dpss.service.interfaces.course;

import com.swp08.dpss.dto.requests.course.CourseLessonRequest;
import com.swp08.dpss.dto.requests.course.CourseRequest;
import com.swp08.dpss.dto.requests.course.LessonProgressRequest;
import com.swp08.dpss.dto.responses.course.CourseEnrollmentResponse;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.course.CourseResponse;
import com.swp08.dpss.dto.responses.course.LessonProgressResponse;
import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.course.CourseLesson;
import com.swp08.dpss.entity.course.LessonProgress;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseRequest request);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    Course updateCourse(Long id, Course updated);

    void softDeleteCourse(Long id);

    void hardDeleteCourse(Long id);

    CourseLessonResponse addLessonToCourse(Long courseId, CourseLessonRequest lesson);

    List<CourseLesson> getLessonsByCourse(Long courseId);

    CourseLessonResponse updateLesson(Long lessonId, CourseLessonRequest updated);

    void deleteLesson(Long lessonId);

    CourseEnrollmentResponse enrollUser(Long courseId, Long userId);

    List<CourseEnrollment> getEnrollmentsByCourse(Long courseId);

    LessonProgressResponse addLessonProgress(LessonProgressRequest progress);

    LessonProgressResponse updateLessonProgress(Long progressId, LessonProgressRequest progress);

    List<LessonProgress> getProgressByEnrollment(Long enrollmentId);
}
