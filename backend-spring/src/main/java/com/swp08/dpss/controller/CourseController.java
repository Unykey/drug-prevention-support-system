package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.CourseRequest;
import com.swp08.dpss.entity.Post;
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

    CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // CRUD for Course
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
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.hardDeleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // CRUD for CourseLesson
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<CourseLesson> addLesson(@PathVariable Long courseId, @RequestBody CourseLesson lesson) {
        return ResponseEntity.ok(courseService.addLessonToCourse(courseId, lesson));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<CourseLesson>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourse(courseId));
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<CourseLesson> updateLesson(@PathVariable Long lessonId, @RequestBody CourseLesson updated) {
        return ResponseEntity.ok(courseService.updateLesson(lessonId, updated));
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        courseService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    // CRUD for CourseEnrollment
    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<CourseEnrollment> enrollUser(@PathVariable Long courseId, @RequestParam Long userId) {
        return ResponseEntity.ok(courseService.enrollUser(courseId, userId));
    }

    @GetMapping("/{courseId}/enrollments")
    public ResponseEntity<List<CourseEnrollment>> getEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getEnrollmentsByCourse(courseId));
    }

    // CRUD for LessonProgress
    @PostMapping("/progress")
    public ResponseEntity<LessonProgress> addProgress(@RequestBody LessonProgress progress) {
        return ResponseEntity.ok(courseService.addLessonProgress(progress));
    }

    @PutMapping("/progress/{progressId}")
    public ResponseEntity<LessonProgress> updateProgress(@PathVariable Long progressId, @RequestBody LessonProgress progress) {
        return ResponseEntity.ok(courseService.updateLessonProgress(progressId, progress));
    }

    @GetMapping("/progress/{enrollmentId}")
    public ResponseEntity<List<LessonProgress>> getProgressByEnrollment(@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(courseService.getProgressByEnrollment(enrollmentId));
    }

}
