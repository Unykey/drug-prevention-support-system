package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.course.CourseLessonRequest;
import com.swp08.dpss.dto.requests.course.CourseRequest;
import com.swp08.dpss.dto.requests.course.LessonProgressRequest;
import com.swp08.dpss.dto.responses.course.*;
import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.course.CourseLesson;
import com.swp08.dpss.entity.course.LessonProgress;
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
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updated) {
        return ResponseEntity.ok(courseService.updateCourse(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteCourse(@PathVariable Long id) {
        courseService.softDeleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> hardDeleteCourse(@PathVariable Long id) {
        courseService.hardDeleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // --- Lesson ---
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<CourseLessonResponse> addLesson(@PathVariable Long courseId, @RequestBody CourseLessonRequest request) {
        return ResponseEntity.ok(courseService.addLessonToCourse(courseId, request));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<CourseLesson>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourse(courseId));
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<CourseLessonResponse> updateLesson(@PathVariable Long lessonId, @RequestBody CourseLessonRequest request) {
        return ResponseEntity.ok(courseService.updateLesson(lessonId, request));
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        courseService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    // --- Enrollment ---
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<CourseEnrollmentResponse> enrollUser(@PathVariable Long courseId, @RequestParam Long userId) {
        return ResponseEntity.ok(courseService.enrollUser(courseId, userId));
    }

    @GetMapping("/{courseId}/enrollments")
    public ResponseEntity<List<CourseEnrollment>> getEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getEnrollmentsByCourse(courseId));
    }

    // --- Progress ---
    @PostMapping("/progress")
    public ResponseEntity<LessonProgressResponse> addProgress(@RequestBody LessonProgressRequest request) {
        return ResponseEntity.ok(courseService.addLessonProgress(request));
    }

    @PutMapping("/progress/{progressId}")
    public ResponseEntity<LessonProgressResponse> updateProgress(@PathVariable Long progressId, @RequestBody LessonProgressRequest request) {
        return ResponseEntity.ok(courseService.updateLessonProgress(progressId, request));
    }

    @GetMapping("/progress/{enrollmentId}")
    public ResponseEntity<List<LessonProgress>> getProgressByEnrollment(@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(courseService.getProgressByEnrollment(enrollmentId));
    }
}
