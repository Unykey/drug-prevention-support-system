package com.swp08.dpss.service.impls.course;

import com.swp08.dpss.dto.requests.course.*;
import com.swp08.dpss.dto.responses.course.*;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.course.*;
import com.swp08.dpss.entity.course.TargetGroup;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseStatus;
import com.swp08.dpss.enums.TargetGroupName;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.course.*;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.course.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseLessonProgressRepository courseLessonProgressRepository;
    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseLessonRepository courseLessonRepository;
    private final LessonProgressRepository  lessonProgressRepository;
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final TargetGroupRepository targetGroupRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseEnrollmentRepository courseEnrollmentRepository, CourseLessonRepository courseLessonRepository, LessonProgressRepository lessonProgressRepository, SurveyRepository surveyRepository, UserRepository userRepository, CourseLessonProgressRepository courseLessonProgressRepository, TargetGroupRepository targetGroupRepository) {
        this.courseRepository = courseRepository;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseLessonRepository = courseLessonRepository;
        this.lessonProgressRepository = lessonProgressRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.courseLessonProgressRepository = courseLessonProgressRepository;
        this.targetGroupRepository = targetGroupRepository;
    }

    @Transactional
    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(request.getStatus());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());

        if (request.getCourseSurveys() != null) {
            for (CourseSurveyRequest csReq : request.getCourseSurveys()) {
                Survey survey = surveyRepository.findById(csReq.getSurveyId())
                        .orElseThrow(() -> new EntityNotFoundException("Survey Not Found with id " + csReq.getSurveyId()));

                CourseSurvey courseSurvey = new CourseSurvey();
                courseSurvey.setCourseSurveyId(new CourseSurveyId()); // you may want to use constructor for clarity
                courseSurvey.setSurvey(survey);
                courseSurvey.setRole(csReq.getRole());

    //            course.addCourseSurvey(courseSurvey);
    //            survey.addCourseSurvey(courseSurvey);
                //^Not needed
                survey.setType(csReq.getSurveyType()); // optional if this is meant to override
            }
        }

        // --- Crucial change for targetGroups ---
        Set<TargetGroup> actualTargetGroups = new HashSet<>();
        for (TargetGroup enumName : request.getTargetGroups()) {
            TargetGroup targetGroupEntity = targetGroupRepository.findByTargetGroupName(enumName.getTargetGroupName())
                    .orElseGet(() -> {
                        // If not found, create a new TargetGroup entity and save it
                        TargetGroup newTargetGroup = new TargetGroup();
                        newTargetGroup.setTargetGroupName(enumName.getTargetGroupName());
                        // You might set a default description or derive it
                        newTargetGroup.setDescription(enumName.getTargetGroupName() + " group");
                        return targetGroupRepository.save(newTargetGroup); // Save the new entity
                    });
            actualTargetGroups.add(targetGroupEntity);
        }
        course.setTargetGroups(actualTargetGroups);
        // --- End crucial change ---

        return toDto(courseRepository.save(course));
    }

    @Transactional
    @Override
    public List<CourseResponse> searchCourses(String keyword, List<String> targetGroups) {
        List<Course> courses;
        Set<TargetGroupName> targetGroupEnums = null;

        if (targetGroups != null && !targetGroups.isEmpty()) {
            targetGroupEnums = targetGroups.stream()
                    .map(s -> {
                        try {
                            return TargetGroupName.valueOf(s.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            // Use a proper logger (e.g., SLF4J) instead of System.err.println
                            // log.warn("Invalid TargetGroupName provided: {}", s);
                            System.err.println("Invalid TargetGroupName provided: " + s); // Keep for now as per your original
                            return null;
                        }
                    })
                    .filter(Objects::nonNull) // Filter out nulls from invalid enum names
                    .collect(Collectors.toSet());
        }

        // --- Updated Search Logic ---
        if ((keyword == null || keyword.isEmpty()) && (targetGroupEnums == null || targetGroupEnums.isEmpty())) {
            courses = courseRepository.findAllCoursesWithAllDetails(); // Use eager fetch for no filters
        } else if (keyword != null && !keyword.isEmpty() && targetGroupEnums != null && !targetGroupEnums.isEmpty()) {
            // This method needs to be defined in CourseRepository
            // Assumes it fetches entities and filters by title and TargetGroupName enum
            courses = courseRepository.findAllByTitleContainingIgnoreCaseAndTargetGroups_TargetGroupNameIn(keyword, targetGroupEnums);
        } else if (keyword != null && !keyword.isEmpty()) {
            courses = courseRepository.findAllByTitleContainingIgnoreCase(keyword); // Assuming this returns entities
        } else if (targetGroupEnums != null && !targetGroupEnums.isEmpty()) {
            // This method needs to be defined in CourseRepository
            // Assumes it fetches entities and filters by TargetGroupName enum
            courses = courseRepository.findAllByTargetGroups_TargetGroupNameIn(targetGroupEnums);
        } else {
            // Fallback: If somehow none of the above, fetch all with details
            courses = courseRepository.findAllCoursesWithAllDetails();
        }
        // --- End Updated Search Logic ---

        return courses.stream()
                .map(this::toDto) // Line 118: Use the toDto mapper
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAllCoursesWithAllDetails(); // Use eager fetch
        return courses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        System.out.println("Is actual transaction active? " + TransactionSynchronizationManager.isActualTransactionActive());

        return toDto(course); // Convert to DTO
    }

    @Transactional
    @Override
    public CourseResponse updateCourse(Long id, CourseRequest updated) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + id));

        // Update basic fields
        course.setTitle(updated.getTitle());
        course.setDescription(updated.getDescription());
        course.setStatus(updated.getStatus());
        course.setStartDate(updated.getStartDate());
        course.setEndDate(updated.getEndDate());

        // --- Handle TargetGroups for Update ---
        Set<TargetGroup> managedTargetGroups = new HashSet<>();
        if (updated.getTargetGroups() != null) {
            for (TargetGroup incomingTargetGroup : updated.getTargetGroups()) {
                // Find existing TargetGroup by its unique name (enum)
                TargetGroup targetGroupEntity = targetGroupRepository.findByTargetGroupName(incomingTargetGroup.getTargetGroupName())
                        .orElseGet(() -> {
                            // If not found, create and persist a new one.
                            TargetGroup newTargetGroup = new TargetGroup();
                            newTargetGroup.setTargetGroupName(incomingTargetGroup.getTargetGroupName());
                            newTargetGroup.setDescription(incomingTargetGroup.getDescription() != null ? incomingTargetGroup.getDescription() : incomingTargetGroup.getTargetGroupName().name() + " group");
                            return targetGroupRepository.save(newTargetGroup);
                        });
                managedTargetGroups.add(targetGroupEntity);
            }
        }
        course.setTargetGroups(managedTargetGroups);

        // Clear existing CourseSurvey links from both sides
        if (course.getCourseSurveyList() != null) {
            // Create a copy to avoid ConcurrentModificationException if modifying the collection during iteration
            new ArrayList<>(course.getCourseSurveyList()).forEach(cs -> {
                if (cs.getSurvey() != null) {
                    cs.getSurvey().removeCourseSurvey(cs); // Disconnect from Survey side
                }
                // If you have a CourseSurveyRepository, you might need to delete the entity explicitly
                // courseSurveyRepository.delete(cs);
            });
            course.getCourseSurveyList().clear(); // Clear from Course side
        }

        // Save course to ensure ID is not null for CourseSurveyId
        course = courseRepository.save(course);

        // Add updated CourseSurvey links
        if (updated.getCourseSurveys() != null) {
            for (CourseSurveyRequest csr : updated.getCourseSurveys()) {
                Survey survey = surveyRepository.findById(csr.getSurveyId())
                        .orElseThrow(() -> new EntityNotFoundException("Survey Not Found with id " + csr.getSurveyId()));

                CourseSurvey courseSurvey = new CourseSurvey();
                courseSurvey.setCourse(course);
                courseSurvey.setSurvey(survey);
                courseSurvey.setRole(csr.getRole());
                courseSurvey.setCourseSurveyId(new CourseSurveyId(course.getId(), survey.getId()));

                // Optional: update type on the Survey entity if needed
                survey.setType(csr.getSurveyType());
            }
        }

        return toDto(courseRepository.save(course));
    }

    @Transactional
    @Override
    public void softDeleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + id));

        course.setStatus(CourseStatus.ARCHIVED);

        if (course.getLessons() != null) {
            for (CourseLesson lesson : new ArrayList<>(course.getLessons())) { // Iterate over copy
                lesson.setCourse(null); // Detach lesson from course
            }
        }

        courseRepository.save(course);
    }

    @Transactional
    @Override
    public void hardDeleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id " + id));

        // Detach and delete lessons
        if (course.getLessons() != null) {
            new HashSet<>(course.getLessons()).forEach(lesson -> { // Iterate over a copy
                lesson.setCourse(null); // Detach
                courseLessonRepository.delete(lesson);
            });
            course.getLessons().clear(); // Clear the collection
        }

        // Detach and delete enrollments and their progress
        if (course.getEnrollments() != null) {
            new HashSet<>(course.getEnrollments()).forEach(enrollment -> { // Iterate over a copy
                if (enrollment.getProgress() != null) {
                    new HashSet<>(enrollment.getProgress()).forEach(progress -> courseLessonProgressRepository.delete(progress));
                    enrollment.getProgress().clear();
                }
                enrollment.setCourse(null);
                if (enrollment.getUser() != null) {
                    enrollment.getUser().removeCourseEnrollment(enrollment);
                }
                courseEnrollmentRepository.delete(enrollment);
            });
            course.getEnrollments().clear();
        }

        // Detach and delete CourseSurveys
        if (course.getCourseSurveyList() != null) {
            new HashSet<>(course.getCourseSurveyList()).forEach(cs -> { // Iterate over a copy
                if (cs.getSurvey() != null) {
                    cs.getSurvey().removeCourseSurvey(cs);
                }
                // You might need to delete the CourseSurvey entity directly if not handled by orphanRemoval
                // courseSurveyRepository.delete(cs); // Uncomment if you have a CourseSurveyRepository
            });
            course.getCourseSurveyList().clear();
        }
        courseRepository.delete(course);
    }

    @Transactional
    @Override
    public void unenrollUser(Long courseId, Long userId) {
        CourseEnrollment enrollment = courseEnrollmentRepository
                .findByCourse_IdAndUser_Id(courseId, userId);

        // Delete lesson progress
        if (enrollment.getProgress() != null) {
            new HashSet<>(enrollment.getProgress()).forEach(progress -> { // Iterate over a copy
                enrollment.removeProgress(progress);
                lessonProgressRepository.delete(progress);
            });
        }

        // Remove from course
        if (enrollment.getCourse() != null) { // Added null check
            enrollment.getCourse().removeEnrollment(enrollment);
        }

        // Remove from user (if you maintain a bidirectional mapping)
        if (enrollment.getUser() != null) { // Added null check
            enrollment.getUser().removeCourseEnrollment(enrollment);
        }

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
        courseLessonRepository.save(lesson);

        return toDto(lesson);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseLessonResponse> getLessonsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course Not Found with id" + courseId));
        return course.getLessons().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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
            new HashSet<>(lesson.getCourseLessonProgressList()).forEach(progress -> { // Iterate over copy
                lesson.removeCourseLessonProgress(progress);
                if (progress.getEnrollment() != null) {
                    progress.getEnrollment().removeProgress(progress);
                }
                courseLessonProgressRepository.delete(progress); // Delete individual progress entries
            });
            lesson.getCourseLessonProgressList().clear(); // Clear the collection
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

    @Transactional
    @Override
    public CourseEnrollmentResponse enroll(CourseEnrollmentRequest request) {
        Long courseId = request.getCourseId();
        Long userId = request.getUserId();
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
    public List<CourseEnrollmentResponse> getEnrollmentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course Not Found with id " + courseId));
        return course.getEnrollments().stream()
                .map(this::toDto) // Line 420: Map entities to DTOs
                .collect(Collectors.toList());
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

        return toDto(lessonProgressRepository.save(progress));
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

    @Transactional (readOnly = true)
    @Override
    public List<LessonProgressResponse> getProgressByEnrollment(CourseEnrollmentId enrollmentId) {
        CourseEnrollment enrollment = courseEnrollmentRepository.findById(enrollmentId).orElseThrow(()-> new EntityNotFoundException("Enrollment Not Found with id " + enrollmentId));
        return enrollment.getProgress().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addSurveyToCourse(long courseId, CourseSurveyRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Survey survey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found with ID: " + request.getSurveyId()));

        CourseSurvey courseSurvey = new CourseSurvey();
        survey.addCourseSurvey(courseSurvey);
        course.addCourseSurvey(courseSurvey);
        courseSurvey.setRole(request.getRole());
        survey.setType(request.getSurveyType());

        // Maintain bidirectional relationship

        surveyRepository.save(survey);
        courseRepository.save(course);
    }

    public CourseResponse toDto(Course course) {
        CourseResponse dto = new CourseResponse();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        dto.setStatus(course.getStatus());
        dto.setPublished(course.isPublished());
        dto.setCreatedAt(course.getCreatedAt());

        // Map TargetGroups (from entity to enum names)
        if (course.getTargetGroups() != null) {
            dto.setTargetGroups(course.getTargetGroups().stream()
                    .map(TargetGroup::getTargetGroupName)
                    .collect(Collectors.toSet()));
        }

        // Map Lessons (from entity to DTO)
        if (course.getLessons() != null) {
            dto.setLessons(course.getLessons().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList()));
        }

        // Map Enrollments (from entity to DTO)
        if (course.getEnrollments() != null) {
            dto.setEnrollments(course.getEnrollments().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList()));
        }

        // Map CourseSurveys (from entity to DTO)
        if (course.getCourseSurveyList() != null) {
            dto.setCourseSurveys(course.getCourseSurveyList().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public CourseLessonResponse toDto(CourseLesson lesson) {
        CourseLessonResponse dto = new CourseLessonResponse();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setType(lesson.getType());
        dto.setContent(lesson.getContent()); // Include content
        dto.setOrderIndex(lesson.getOrderIndex());
        dto.setCourseId(lesson.getCourse() != null ? lesson.getCourse().getId() : null);
        // Assuming CourseLesson entity doesn't directly have surveyId, if it does, map it.
        // If surveyId comes from CourseLessonRequest, you'd need to store it in the entity.
        // For now, leaving it null as per CourseLesson entity structure.
        dto.setSurveyId(null); // Set to null or map if available in entity
        return dto;
    }

    public CourseEnrollmentResponse toDto(CourseEnrollment enrollment) {
        CourseEnrollmentResponse dto = new CourseEnrollmentResponse();
        dto.setUserId(enrollment.getUser().getId());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }

    public CourseSurveyResponse toDto(CourseSurvey courseSurvey) {
        CourseSurveyResponse dto = new CourseSurveyResponse();
        dto.setSurveyId(courseSurvey.getSurvey().getId());
        dto.setRole(courseSurvey.getRole());
        dto.setSurveyType(courseSurvey.getSurvey().getType()); // Assuming Survey entity has a getType()
        return dto;
    }

    public LessonProgressResponse toDto(CourseLessonProgress progress) {
        LessonProgressResponse dto = new LessonProgressResponse();
        dto.setId(progress.getId());
        // Map user, course, and lesson IDs for comprehensive tracking
        if (progress.getEnrollment() != null && progress.getEnrollment().getUser() != null) {
            dto.setUserId(progress.getEnrollment().getUser().getId());
        }
        if (progress.getEnrollment() != null && progress.getEnrollment().getCourse() != null) {
            dto.setCourseId(progress.getEnrollment().getCourse().getId());
        }
        if (progress.getLesson() != null) {
            dto.setLessonId(progress.getLesson().getId());
        }
        dto.setCompleted(progress.isCompleted());
        dto.setCompletedAt(progress.getCompletedAt());
        return dto;
    }
}
