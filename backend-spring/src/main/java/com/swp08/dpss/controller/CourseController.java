package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.course.CourseLessonRequest;
import com.swp08.dpss.dto.requests.course.CourseRequest;
import com.swp08.dpss.dto.requests.course.LessonProgressRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.course.CourseEnrollmentResponse;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.course.LessonProgressResponse;
import com.swp08.dpss.entity.course.*;
import com.swp08.dpss.service.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // --- Course ---
    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody CourseRequest request) {
        Course course = courseService.createCourse(request);
        return ResponseEntity.ok(new ApiResponse<>(true, course, "Course created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses(@RequestParam(name = "keyword", required = false) String keyword) {
        List<Course> result = (keyword != null && !keyword.isEmpty())
                ? courseService.searchCoursesByName(keyword)
                : courseService.getAllCourses();
        return ResponseEntity.ok(new ApiResponse<>(true, result, "Courses retrieved"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.getCourseById(id), "Course retrieved"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @RequestBody CourseRequest updated) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.updateCourse(id, updated), "Course updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteCourse(@PathVariable Long id) {
        courseService.softDeleteCourse(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Course soft-deleted"));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<Void>> hardDeleteCourse(@PathVariable Long id) {
        courseService.hardDeleteCourse(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Course hard-deleted"));
    }

    @DeleteMapping("/{courseId}/unenroll")
    public ResponseEntity<ApiResponse<Void>> unenrollUser(@PathVariable Long courseId, @RequestParam Long userId) {
        courseService.unenrollUser(courseId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "User unenrolled"));
    }

    // --- Lesson ---
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<ApiResponse<CourseLessonResponse>> addLesson(@PathVariable Long courseId, @RequestBody CourseLessonRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.addLessonToCourse(courseId, request), "Lesson added"));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<ApiResponse<List<CourseLesson>>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.getLessonsByCourse(courseId), "Lessons retrieved"));
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<CourseLessonResponse>> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.getLesson(lessonId), "Lesson retrieved"));
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<CourseLessonResponse>> updateLesson(@PathVariable Long lessonId, @RequestBody CourseLessonRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.updateLesson(lessonId, request), "Lesson updated"));
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable Long lessonId) {
        courseService.deleteLesson(lessonId);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Lesson deleted"));
    }

    // --- Enrollment ---
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponse>> enrollUser(@PathVariable Long courseId, @RequestParam Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.enrollUser(courseId, userId), "User enrolled"));
    }

    @GetMapping("/{courseId}/enrollments")
    public ResponseEntity<ApiResponse<List<CourseEnrollment>>> getEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.getEnrollmentsByCourse(courseId), "Enrollments retrieved"));
    }

    // --- Progress ---
    @PostMapping("/progress")
    public ResponseEntity<ApiResponse<LessonProgressResponse>> addProgress(@RequestBody LessonProgressRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.addLessonProgress(request), "Progress added"));
    }

    @PutMapping("/progress/{progressId}")
    public ResponseEntity<ApiResponse<LessonProgressResponse>> updateProgress(@PathVariable Long progressId, @RequestBody LessonProgressRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.updateLessonProgress(progressId, request), "Progress updated"));
    }

    @GetMapping("/progress/{enrollmentId}")
    public ResponseEntity<ApiResponse<List<CourseLessonProgress>>> getProgressByEnrollment(@PathVariable CourseEnrollmentId enrollmentId) {
        return ResponseEntity.ok(new ApiResponse<>(true, courseService.getProgressByEnrollment(enrollmentId), "Progress retrieved"));
    }
}
