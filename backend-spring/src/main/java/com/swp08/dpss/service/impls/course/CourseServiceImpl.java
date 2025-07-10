package com.swp08.dpss.service.impls.course;

import com.swp08.dpss.dto.requests.course.CourseLessonRequest;
import com.swp08.dpss.dto.requests.course.CourseRequest;
import com.swp08.dpss.dto.requests.course.LessonProgressRequest;
import com.swp08.dpss.dto.responses.course.CourseEnrollmentResponse;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.course.CourseResponse;
import com.swp08.dpss.dto.responses.course.LessonProgressResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.course.CourseLesson;
import com.swp08.dpss.entity.course.LessonProgress;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseStatus;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.course.CourseEnrollmentRepository;
import com.swp08.dpss.repository.course.CourseLessonRepository;
import com.swp08.dpss.repository.course.CourseRepository;
import com.swp08.dpss.repository.course.LessonProgressRepository;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.course.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    CourseEnrollmentRepository courseEnrollmentRepository;
    CourseLessonRepository courseLessonRepository;
    LessonProgressRepository  lessonProgressRepository;
    SurveyRepository surveyRepository;
    UserRepository userRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseEnrollmentRepository courseEnrollmentRepository, CourseLessonRepository courseLessonRepository, LessonProgressRepository lessonProgressRepository) {
        this.courseRepository = courseRepository;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseLessonRepository = courseLessonRepository;
        this.lessonProgressRepository = lessonProgressRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    @Transactional
    @Override
    public Course updateCourse(Long id, Course updated) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + id));

        course.setTitle(updated.getTitle());
        course.setDescription(updated.getDescription());
        course.setStatus(updated.getStatus());
        course.setTargetGroups(updated.getTargetGroups());
        course.setStartDate(updated.getStartDate());
        course.setEndDate(updated.getEndDate());

        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public void softDeleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + id));

        course.setStatus(CourseStatus.ARCHIVED);

        // Optionally mark lessons as archived too, if you plan soft-deletion per lesson later
        for (CourseLesson lesson : course.getLessons()) {
            lesson.setCourse(null); // to prevent cascade issues later
        }

        courseRepository.save(course);
    }

    @Transactional
    @Override
    public void hardDeleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + id));

        // Detach lessons
        for (CourseLesson lesson : course.getLessons()) {
            lesson.setCourse(null);
            courseLessonRepository.delete(lesson);
        }
        course.getLessons().clear();

        // Detach enrollments
        for (CourseEnrollment enrollment : course.getEnrollments()) {
            enrollment.setCourse(null);
            courseEnrollmentRepository.delete(enrollment);
            enrollment.getUser().removeCourseEnrollment(enrollment);
        }
        course.getEnrollments().clear();

        courseRepository.delete(course);
    }

    @Transactional
    @Override
    public void unenrollUser(Long courseId, Long userId) {
        CourseEnrollment enrollment = courseEnrollmentRepository
                .findByCourse_IdAndUser_Id(courseId, userId);

        // Delete lesson progress
        for (LessonProgress progress : enrollment.getProgress()) {
            enrollment.removeProgress(progress);
            lessonProgressRepository.delete(progress);
        }

        // Remove from course
        enrollment.getCourse().removeEnrollment(enrollment);

        // Remove from user (if you maintain a bidirectional mapping)
        enrollment.getUser().removeCourseEnrollment(enrollment);

        // Delete enrollment
        courseEnrollmentRepository.delete(enrollment);
    }


    @Transactional
    @Override
    public CourseLessonResponse addLessonToCourse(Long courseId, CourseLessonRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + courseId));

        CourseLesson lesson = new CourseLesson();
        lesson.setTitle(request.getTitle());
        lesson.setType(request.getType());
        lesson.setContent(request.getContent());
        lesson.setOrderIndex(request.getOrderIndex());
        lesson.setCourse(course);

        if (request.getSurveyId() != null) {
            Survey survey = surveyRepository.findById(request.getSurveyId())
                    .orElseThrow(() -> new EntityNotFoundException("Survey Not Found with id " + request.getSurveyId()));
            lesson.setSurvey(survey);
        }

        course.addCourseLesson(lesson);
        courseRepository.save(course); // or use courseLessonRepository.save(lesson) if available

        return toDto(lesson);
    }

    @Override
    public List<CourseLesson> getLessonsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new EntityNotFoundException("Course Not Found with id" + courseId));
        return course.getLessons();
    }

    @Override
    public CourseLessonResponse getLesson(Long lessonId) {
        CourseLesson lesson = courseLessonRepository.findById(lessonId).orElseThrow(()-> new EntityNotFoundException("Lesson Not Found with id " + lessonId));
        return toDto(lesson);
    }

    @Transactional
    @Override
    public CourseLessonResponse updateLesson(Long lessonId, CourseLessonRequest updated) {
        CourseLesson lesson = courseLessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id " + lessonId));


        lesson.setTitle(updated.getTitle());
        lesson.setType(updated.getType());
        lesson.setContent(updated.getContent());
        lesson.setOrderIndex(updated.getOrderIndex());

        if (updated.getSurveyId() != null) {
            Survey survey = surveyRepository.findById(updated.getSurveyId())
                    .orElseThrow(() -> new EntityNotFoundException("Survey Not Found with id " + updated.getSurveyId()));
            lesson.setSurvey(survey);
        } else {
            lesson.setSurvey(null);
        }

        courseLessonRepository.save(lesson);
        return toDto(lesson);
    }

    @Override
    @Transactional
    public void deleteLesson(Long lessonId) {
        CourseLesson lesson = courseLessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson Not Found with id " + lessonId));

        // Detach from course
        if (lesson.getCourse() != null) {
            lesson.getCourse().getLessons().remove(lesson);
            lesson.setCourse(null);
        }

        // Detach from survey
        if (lesson.getSurvey() != null) {
            lesson.setSurvey(null); // Optional: if Survey is reused, don't delete it
        }

        // Detach lesson progress if exists
        if (lesson.getLessonProgress() != null) {
            LessonProgress progress = lesson.getLessonProgress();
            progress.setLesson(null);
            progress.setEnrollment(null); // unlink both sides if needed
            lesson.setLessonProgress(null);
            lessonProgressRepository.delete(progress);
        }

        courseLessonRepository.delete(lesson);
    }


    @Transactional
    @Override
    public CourseEnrollmentResponse enrollUser(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new EntityNotFoundException("Course Not Found with id " + courseId));
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User not found with id " + userId));
        CourseEnrollment enrollment = new CourseEnrollment();
        course.addEnrollment(enrollment);
        user.addCourseEnrollment(enrollment);
        courseEnrollmentRepository.save(enrollment);
        return toDto(enrollment);
    }

    @Override
    public List<CourseEnrollment> getEnrollmentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new EntityNotFoundException("Course Not Found with id " + courseId));
        return course.getEnrollments();
    }

    @Transactional
    @Override
    public LessonProgressResponse addLessonProgress(LessonProgressRequest request) {
        CourseEnrollment enrollment = courseEnrollmentRepository.findById(request.getEnrollmentId())
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));

        CourseLesson lesson = courseLessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        LessonProgress progress = new LessonProgress();
        enrollment.addProgress(progress);
        lesson.setLessonProgress(progress);
        progress.setLesson(lesson);
        progress.setCompleted(request.isCompleted());
        progress.setCompletedAt(request.isCompleted() ? LocalDateTime.now() : null);

        lessonProgressRepository.save(progress);

        return toDto(progress);
    }

    @Transactional
    @Override
    public LessonProgressResponse updateLessonProgress(Long progressId, LessonProgressRequest request) {
        CourseLesson lesson = courseLessonRepository.findById(request.getLessonId()).orElseThrow(()-> new EntityNotFoundException("Lesson not found with id " + request.getLessonId()));
        CourseEnrollment enrollment = courseEnrollmentRepository.findById(request.getEnrollmentId()).orElseThrow(()-> new EntityNotFoundException("Enrollment not found with id " + request.getEnrollmentId()));
        LessonProgress progress = lessonProgressRepository.findById(progressId)
                .orElseThrow(() -> new EntityNotFoundException("Progress not found with id " + progressId));
        progress.setCompleted(request.isCompleted());
        progress.setCompletedAt(request.isCompleted() ? LocalDateTime.now() : null);
        progress.setLesson(lesson);
        lesson.setLessonProgress(progress);
        enrollment.addProgress(progress);
        lessonProgressRepository.save(progress);

        return toDto(progress);
    }


    @Override
    public List<LessonProgress> getProgressByEnrollment(Long enrollmentId) {
        CourseEnrollment enrollment = courseEnrollmentRepository.findById(enrollmentId).orElseThrow(()-> new EntityNotFoundException("Enrollment Not Found with id " + enrollmentId));
        return enrollment.getProgress();
    }

    public CourseResponse toDto(Course course) {
        CourseResponse dto = new CourseResponse();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStatus(course.getStatus());
        dto.setTargetGroups(course.getTargetGroups());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        return dto;
    }

    public CourseLessonResponse toDto(CourseLesson lesson) {
        CourseLessonResponse dto = new CourseLessonResponse();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setType(lesson.getType());
        dto.setContent(lesson.getContent());
        dto.setOrderIndex(lesson.getOrderIndex());
        dto.setCourseId(lesson.getCourse() != null ? lesson.getCourse().getId() : null);
        dto.setSurveyId(lesson.getSurvey() != null ? lesson.getSurvey().getId() : null);
        return dto;
    }

    public CourseEnrollmentResponse toDto(CourseEnrollment enrollment) {
        CourseEnrollmentResponse dto = new CourseEnrollmentResponse();
        dto.setId(enrollment.getId());
        dto.setUserId(enrollment.getUser() != null ? enrollment.getUser().getId() : null);
        dto.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }

    public LessonProgressResponse toDto(LessonProgress progress) {
        LessonProgressResponse dto = new LessonProgressResponse();
        dto.setId(progress.getId());
        dto.setEnrollmentId(progress.getEnrollment().getId());
        dto.setLessonId(progress.getLesson().getId());
        dto.setCompleted(progress.isCompleted());
        dto.setCompletedAt(progress.getCompletedAt());
        return dto;
    }

}
