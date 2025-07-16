package com.swp08.dpss.service.impls.course;

import com.swp08.dpss.dto.requests.course.CourseLessonRequest;
import com.swp08.dpss.dto.requests.course.CourseRequest;
import com.swp08.dpss.dto.requests.course.LessonProgressRequest;
import com.swp08.dpss.dto.responses.course.CourseEnrollmentResponse;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.course.CourseResponse;
import com.swp08.dpss.dto.responses.course.LessonProgressResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.course.*;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseStatus;
import com.swp08.dpss.enums.ProgramSurveyRoles;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.course.*;
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
    private final CourseLessonProgressRepository courseLessonProgressRepository;
    CourseRepository courseRepository;
    CourseEnrollmentRepository courseEnrollmentRepository;
    CourseLessonRepository courseLessonRepository;
    LessonProgressRepository  lessonProgressRepository;
    SurveyRepository surveyRepository;
    UserRepository userRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseEnrollmentRepository courseEnrollmentRepository, CourseLessonRepository courseLessonRepository, LessonProgressRepository lessonProgressRepository, SurveyRepository surveyRepository, UserRepository userRepository, CourseLessonProgressRepository courseLessonProgressRepository) {
        this.courseRepository = courseRepository;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseLessonRepository = courseLessonRepository;
        this.lessonProgressRepository = lessonProgressRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.courseLessonProgressRepository = courseLessonProgressRepository;
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

        CourseSurvey courseSurvey = new CourseSurvey();
        courseSurvey.setCourseSurveyId(new CourseSurveyId());
        course.addCourseSurvey(courseSurvey);

        for (Long s : request.getSurveyId()){
            Survey survey = surveyRepository.findById(s).orElseThrow(() -> new EntityNotFoundException("Survey Not Found"));
            survey.addCourseSurvey(courseSurvey);
            //TODO: Set type
            //survey.setType(request.getSurveyRole());
        }

        courseRepository.save(course);
        return course;
    }

    @Override
    public List<Course> searchCoursesByName(String keyword) {
        return courseRepository.findAllByTitleContainingIgnoreCase(keyword);
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
    public Course updateCourse(Long id, CourseRequest updated) {
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
        for (CourseLessonProgress progress : enrollment.getProgress()) {
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

        course.addLesson(lesson);
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
            lesson.getCourse().removeLesson(lesson);
        }

        // Detach lesson progress if exists
        if (lesson.getCourseLessonProgressList() != null) {
            for (CourseLessonProgress progress : lesson.getCourseLessonProgressList()) { // unlink both sides if needed
                lesson.removeCourseLessonProgress(progress);
                progress.getEnrollment().removeProgress(progress);
            }
//            courseLessonProgressRepository.deleteAll(lesson.getCourseLessonProgressList()); //Not sure if this command is necessary
        }
        courseLessonRepository.delete(lesson);
    }


    @Transactional
    @Override
    public CourseEnrollmentResponse enrollUser(Long courseId, Long userId) {
        if (courseEnrollmentRepository.existsById(new CourseEnrollmentId(userId, courseId))) {
            throw new IllegalStateException("User is already enrolled in this course");
        }
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new EntityNotFoundException("Course Not Found with id " + courseId));
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User not found with id " + userId));

        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);
        enrollment.setId(new CourseEnrollmentId(user.getId(), course.getId()));
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

        CourseLessonProgress progress = new CourseLessonProgress();
        enrollment.addProgress(progress);
        lesson.addCourseLessonProgress(progress);

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

        CourseLessonProgress progress = lessonProgressRepository.findById(progressId)
                .orElseThrow(() -> new EntityNotFoundException("Progress not found with id " + progressId));
        progress.setCompleted(request.isCompleted());
        progress.setCompletedAt(request.isCompleted() ? LocalDateTime.now() : null);
        lessonProgressRepository.save(progress);
        return toDto(progress);
    }

    @Override
    public List<CourseLessonProgress> getProgressByEnrollment(CourseEnrollmentId enrollmentId) {
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
        return dto;
    }

    public CourseEnrollmentResponse toDto(CourseEnrollment enrollment) {
        CourseEnrollmentResponse dto = new CourseEnrollmentResponse();
        dto.setUserId(enrollment.getUser().getId());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }

    public LessonProgressResponse toDto(CourseLessonProgress progress) {
        LessonProgressResponse dto = new LessonProgressResponse();
        dto.setId(progress.getId());
        dto.setUserId(progress.getEnrollment().getUser().getId());
        dto.setSurveyId(progress.getEnrollment().getCourse().getId());
        dto.setLessonId(progress.getLesson().getId());
        dto.setCompleted(progress.isCompleted());
        dto.setCompletedAt(progress.getCompletedAt());
        return dto;
    }

}
