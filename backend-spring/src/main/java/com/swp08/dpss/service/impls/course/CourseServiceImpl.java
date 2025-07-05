package com.swp08.dpss.service.impls.course;

import com.swp08.dpss.dto.requests.CourseRequest;
import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.course.CourseLesson;
import com.swp08.dpss.entity.course.LessonProgress;
import com.swp08.dpss.enums.CourseStatus;
import com.swp08.dpss.repository.course.CourseEnrollmentRepository;
import com.swp08.dpss.repository.course.CourseLessonRepository;
import com.swp08.dpss.repository.course.CourseRepository;
import com.swp08.dpss.repository.course.LessonProgressRepository;
import com.swp08.dpss.service.interfaces.course.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    CourseEnrollmentRepository courseEnrollmentRepository;
    CourseLessonRepository courseLessonRepository;
    LessonProgressRepository  lessonProgressRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseEnrollmentRepository courseEnrollmentRepository, CourseLessonRepository courseLessonRepository, LessonProgressRepository lessonProgressRepository) {
        this.courseRepository = courseRepository;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseLessonRepository = courseLessonRepository;
        this.lessonProgressRepository = lessonProgressRepository;
    }

    @Override
    public Course createCourse(CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(request.getStatus());
        course.setTargetGroups(request.getTargetGroups());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());

        courseRepository.save(course);
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public Course updateCourse(Long id, Course updated) {
        return null;
    }

    @Override
    public void softDeleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course Not Found with id" + id));
        course.setStatus(CourseStatus.ARCHIVED);
        courseRepository.save(course);
    }

    @Override
    public void hardDeleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course Not Found with id" + id));
        for (CourseLesson l : course.getLessons()){
            l.setCourse(null);
        }
        for (CourseEnrollment e : course.getEnrollments()){
            e.setCourse(null);
        }
        courseRepository.delete(course);
    }

    @Override
    public CourseLesson addLessonToCourse(Long courseId, CourseLesson lesson) {
        return null;
    }

    @Override
    public List<CourseLesson> getLessonsByCourse(Long courseId) {
        return List.of();
    }

    @Override
    public CourseLesson updateLesson(Long lessonId, CourseLesson updated) {
        return null;
    }

    @Override
    public void deleteLesson(Long lessonId) {

    }

    @Override
    public CourseEnrollment enrollUser(Long courseId, Long userId) {
        return null;
    }

    @Override
    public List<CourseEnrollment> getEnrollmentsByCourse(Long courseId) {
        return List.of();
    }

    @Override
    public LessonProgress addLessonProgress(LessonProgress progress) {
        return null;
    }

    @Override
    public LessonProgress updateLessonProgress(Long progressId, LessonProgress progress) {
        return null;
    }

    @Override
    public List<LessonProgress> getProgressByEnrollment(Long enrollmentId) {
        return List.of();
    }
}
