package com.swp08.dpss.config;

import com.swp08.dpss.dto.requests.survey.BulkSubmitSurveyAnswerRequest; // Ensure this is correctly imported
import com.swp08.dpss.dto.requests.survey.BulkSubmitSurveyAnswerRequest.AnswerSubmission; // Import inner class
import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.GuardianCreationRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.requests.course.*;
import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.QuestionOptionRequest; // New import
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.entity.course.CourseEnrollmentId;
import com.swp08.dpss.entity.course.CourseLesson;
import com.swp08.dpss.entity.course.TargetGroup;
import com.swp08.dpss.enums.*;
import com.swp08.dpss.repository.course.TargetGroupRepository;
import com.swp08.dpss.service.interfaces.UserService;

import com.swp08.dpss.service.interfaces.course.CourseService;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import com.swp08.dpss.service.interfaces.survey.SurveyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j //Log stuffs :D
@Component
@AllArgsConstructor
@Profile("local")
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final SurveyService surveyService;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyAnswerService surveyAnswerService;
    private final CourseService courseService;
    private final TargetGroupRepository  targetGroupRepository;

    @Override
    public void run(String... args) throws Exception {
        // Add User
        try {
            userInit();
            log.info("User initialization complete.");
        } catch (Exception e) {
            log.error("User initialization failed:", e);
        }
        // Add Survey
//        try {
//            surveyInit();
//            log.info("Survey initialization complete.");
//        } catch (Exception e) {
//            log.error("Survey initialization failed:", e);
//        }
//
//        // Add Survey Answer
//        try {
//            answerInit();
//            log.info("Survey Answer initialization complete.");
//        } catch (Exception e) {
//            log.error("Survey Answer initialization failed: ", e);
//        }

        // Initialize target groups before courses
        try {
            targetGroupInit();
            log.info("TargetGroup initialization complete.");
        } catch (Exception e) {
            log.error("TargetGroup initialization failed: ", e);
        }
        // Add Course
        try {
            courseInit();
            log.info("Course initialization complete.");
        } catch (Exception e) {
            log.error("Course initialization failed: ", e);
        }

        // Add Course Enrollment
        try {
            courseEnrollmentInit();
            log.info("CourseEnrollment initialization complete.");
        } catch (Exception e) {
            log.error("CourseEnrollment initialization failed: ", e);
        }

        try {
            courseLessonInit();
            log.info("CourseLesson initialization complete.");
        } catch (Exception e) {
            log.error("CourseLesson initialization failed: ", e);
        }

        // Add Lesson Progress
        try {
            lessonProgressInit();
            log.info("Lesson Progress initialization complete.");
        } catch (Exception e) {
            log.error("Lesson Progress initialization failed: ", e);
        }
        log.info("DataInitializer finished.");
    }

    private void userInit() {
        // ===== Guest Self-Registration =====
        // No Guardian
        UserCreationRequest member1 = new UserCreationRequest("Ulises Sieghard", "wP6!4.!o=Z", Genders.MALE, LocalDate.of(1999, 1, 4), "sieghard@eventbrite.com", "0934567890", null);

        UserCreationRequest member2 = new UserCreationRequest("Sammy Cudde", "yN6/f=wX", Genders.FEMALE, LocalDate.of(2003, 1, 4), "samdde@acquirethisname.com", "0935367812", null);

        // Each member has a guardian
        GuardianCreationRequest guardian_m3 = new GuardianCreationRequest("Madel Mutton","mamutton@marketwatch.com", "0681507354", null);

        UserCreationRequest member3 = new UserCreationRequest("Yorker Mutton", "lS2~_ma!A77", null, LocalDate.of(2009, 6, 4), "ymutton5@acquirethisname.com", "0681507354", guardian_m3);

        GuardianCreationRequest guardian_m4 = new GuardianCreationRequest("Innis Betters","eriss@marketwatch.com", "0681506475", null);

        UserCreationRequest member4 = new UserCreationRequest("Napppie Rysom", "lS2~_ma!A77", Genders.PREFER_NOT_TO_SAY, LocalDate.of(2015, 6, 4), "rysom@acquirethisname.com", "0451567354", guardian_m4);

        userService.register(member1);
        userService.register(member2);
        userService.register(member3);
        userService.register(member4);
        log.info("Created User");

        // ===== Admin-Created Accounts =====
        // Consultant role
        AdminUserCreationRequest cons1 = new AdminUserCreationRequest("cons1", "qweasdzxc", Roles.CONSULTANT, Genders.NON_BINARY, LocalDate.of(1980, 1, 4), "cons1@example.com", "094232342", null);

        AdminUserCreationRequest cons2 = new AdminUserCreationRequest("cons2", "qweasdzxc", Roles.CONSULTANT, Genders.MALE, LocalDate.of(2000, 1, 4), "cons2@example.com", "094232123", null);

        AdminUserCreationRequest cons3 = new AdminUserCreationRequest("cons3", "qweasdzxc", Roles.CONSULTANT, null, LocalDate.of(2003, 1, 4), "cons13@example.com", "012232342", null);

        // Staff role
        AdminUserCreationRequest staff1 = new AdminUserCreationRequest("staff1", "qweasdzxc", Roles.STAFF, Genders.NON_BINARY, LocalDate.of(1980, 1, 4), "staff1@example.com", "0934566542", null);

        AdminUserCreationRequest staff2 = new AdminUserCreationRequest("staff2", "qweasdzxc", Roles.STAFF, null, LocalDate.of(1980, 5, 4), "staff2@example.com", "0232366542",  null);

        // Manager role
        AdminUserCreationRequest man1 = new AdminUserCreationRequest("man1", "qweasdzxc", Roles.MANAGER, Genders.FEMALE, LocalDate.of(1980, 1, 4), "man1@example.com", "0123675432", null);

        // Admin role
        AdminUserCreationRequest ad1 = new AdminUserCreationRequest("ad1", "qweasdzxc", Roles.ADMIN, Genders.MALE, LocalDate.of(1980, 1, 4), "ad1@example.com", "0343124643", null);

        AdminUserCreationRequest ad2 = new AdminUserCreationRequest("admintest", "12345678", Roles.ADMIN, Genders.MALE, LocalDate.of(1980, 1, 4), "admintest@example.com", "3141592653589", null);

        userService.createNewUser(cons1);
        userService.createNewUser(cons2);
        userService.createNewUser(cons3);
        userService.createNewUser(staff1);
        userService.createNewUser(staff2);
        userService.createNewUser(man1);
        userService.createNewUser(ad1);
        userService.createNewUser(ad2);
        log.info("Admin Created User with Roles");
    }

    private void surveyInit() {
        SurveyDetailsDto assist = surveyService.createSurvey(
                new CreateSurveyRequest("ASSIST", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Đánh giá mức độ sử dụng chất gây nghiện"));

        SurveyDetailsDto crafft = surveyService.createSurvey(
                new CreateSurveyRequest("CRAFFT", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Sàng lọc nguy cơ sử dụng chất gây nghiện"));

        SurveyDetailsDto dast10 = surveyService.createSurvey(
                new CreateSurveyRequest("DAST-10", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Khảo sát mức độ nghiện các loại ma túy"));

        // Define options as List<QuestionOptionRequest>
        List<QuestionOptionRequest> yesNoOptions = List.of(
                new QuestionOptionRequest("Có", true), // "Có" is correct for some questions
                new QuestionOptionRequest("Không", false) // "Không" is correct for others
        );
        List<QuestionOptionRequest> noYesOptions = List.of(
                new QuestionOptionRequest("Có", false),
                new QuestionOptionRequest("Không", true)
        );


        // SurveyQuestionRequest now expects List<QuestionOptionRequest>
        surveyQuestionService.addQuestionToSurvey(assist.getId(),
                new SurveyQuestionRequest("Bạn đã từng sử dụng rượu chưa?", QuestionTypes.YN, yesNoOptions)); // Use the list of options

        surveyQuestionService.addQuestionToSurvey(assist.getId(),
                new SurveyQuestionRequest("Bạn có hút thuốc lá không?", QuestionTypes.YN, noYesOptions)); // Use the list of options

        surveyQuestionService.addQuestionToSurvey(crafft.getId(),
                new SurveyQuestionRequest("Bạn từng đi xe có người dùng chất?", QuestionTypes.YN, yesNoOptions)); // Use the list of options

        surveyQuestionService.addQuestionToSurvey(crafft.getId(),
                new SurveyQuestionRequest("Bạn có muốn giảm sử dụng chất đó không?", QuestionTypes.YN, yesNoOptions)); // Use the list of options

        surveyQuestionService.addQuestionToSurvey(dast10.getId(),
                new SurveyQuestionRequest("Bạn dùng heroin 30 ngày qua?", QuestionTypes.YN, noYesOptions)); // Use the list of options

        surveyQuestionService.addQuestionToSurvey(dast10.getId(),
                new SurveyQuestionRequest("Bạn thấy khó chịu nếu không dùng chất?", QuestionTypes.YN, yesNoOptions)); // Use the list of options
    }


    private void answerInit() {
        SurveyDetailsDto survey = surveyService.createSurvey(
                new CreateSurveyRequest("DEMO_SURVEY", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Demo survey for testing answers"));
        Long surveyId = survey.getId();

        // Define options as List<QuestionOptionRequest>
        List<QuestionOptionRequest> yesNoOptionsForQ1 = List.of(
                new QuestionOptionRequest("Có", true), // Assuming "Có" is the correct answer
                new QuestionOptionRequest("Không", false)
        );
        List<QuestionOptionRequest> noYesOptionsForQ2 = List.of(
                new QuestionOptionRequest("Có", false),
                new QuestionOptionRequest("Không", true) // Assuming "Không" is the correct answer
        );


        // Ensure SurveyQuestionRequest uses QuestionTypes enum
        SurveyQuestionDto q1 = surveyQuestionService.addQuestionToSurvey(surveyId,
                new SurveyQuestionRequest("Bạn có uống rượu không?", QuestionTypes.YN, yesNoOptionsForQ1));

        SurveyQuestionDto q2 = surveyQuestionService.addQuestionToSurvey(surveyId,
                new SurveyQuestionRequest("Bạn có hút thuốc không?", QuestionTypes.YN, noYesOptionsForQ2));

        String user1Email = "sieghard@eventbrite.com"; // Get email from your userInit()

        // Submit individual answers for testing the single submitAnswer method
        SubmitSurveyAnswerRequest answer1 = new SubmitSurveyAnswerRequest(null, "Có"); // content for q1
        SubmitSurveyAnswerRequest answer2 = new SubmitSurveyAnswerRequest(null, "Không"); // content for q2

        surveyAnswerService.submitAnswer(surveyId, q1.getId(), answer1, user1Email);
        surveyAnswerService.submitAnswer(surveyId, q2.getId(), answer2, user1Email);

        // --- Demonstrate Bulk Answer Submission ---
        String user2Email = "samdde@acquirethisname.com"; // Another user

        // Create a BulkSubmitSurveyAnswerRequest
        BulkSubmitSurveyAnswerRequest bulkRequest = new BulkSubmitSurveyAnswerRequest();
        bulkRequest.setSurveyId(surveyId);
        // Assuming userId will be determined from userEmail in service for security
        // bulkRequest.setUserId(2L); // This field might be redundant if user is derived from principal

        List<AnswerSubmission> bulkAnswers = new ArrayList<>();
        // Answer for q1 (user2 answers "Không")
        bulkAnswers.add(new AnswerSubmission(q1.getId(), "Không"));
        // Answer for q2 (user2 answers "Có")
        bulkAnswers.add(new AnswerSubmission(q2.getId(), "Có"));

        bulkRequest.setAnswers(bulkAnswers);

        // Submit all answers in bulk
        log.info("Submitting bulk answers for user: {}", user2Email);
        surveyAnswerService.submitAllAnswers(surveyId, bulkRequest, user2Email);
        log.info("Bulk answers submission complete for user: {}", user2Email);
    }

    private void targetGroupInit() {
        // Create STUDENT target group if it doesn't exist
        if (targetGroupRepository.findByTargetGroupName(TargetGroupName.STUDENT).isEmpty()) {
            TargetGroup studentTg = new TargetGroup();
            studentTg.setTargetGroupName(TargetGroupName.STUDENT);
            studentTg.setDescription("Courses for students");
            targetGroupRepository.save(studentTg);
            log.info("Created STUDENT TargetGroup.");
        }

        // Create TEACHER target group if it doesn't exist
        if (targetGroupRepository.findByTargetGroupName(TargetGroupName.TEACHER).isEmpty()) {
            TargetGroup teacherTg = new TargetGroup();
            teacherTg.setTargetGroupName(TargetGroupName.TEACHER);
            teacherTg.setDescription("Courses for teachers");
            targetGroupRepository.save(teacherTg);
            log.info("Created TEACHER TargetGroup.");
        }

        // Create GUARDIAN target group if it doesn't exist
        if (targetGroupRepository.findByTargetGroupName(TargetGroupName.GUARDIAN).isEmpty()) {
            TargetGroup guardianTg = new TargetGroup();
            guardianTg.setTargetGroupName(TargetGroupName.GUARDIAN);
            guardianTg.setDescription("Courses for guardians/parents");
            targetGroupRepository.save(guardianTg);
            log.info("Created GUARDIAN TargetGroup.");
        }
    }

    private void courseInit() {
        TargetGroup studentTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.STUDENT).orElseThrow(() -> new RuntimeException("Target group Student not found"));
        TargetGroup teacherTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.TEACHER).orElseThrow(() -> new RuntimeException("Target group Teacher not found"));
        TargetGroup guardianTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.GUARDIAN).orElseThrow(() -> new RuntimeException("Target group Guardian not found"));
        //

        // Course 1 (will likely have ID 1L)
        CourseRequest course1 = new CourseRequest();
        course1.setTitle("Introduction to Mental Health");
        course1.setDescription("A course about understanding mental well-being");
        course1.setStatus(CourseStatus.PUBLISHED);
        course1.setStartDate(LocalDate.now());
        course1.setEndDate(LocalDate.now().plusWeeks(4));
        course1.setTargetGroups(Set.of(studentTargetGroupOpt, guardianTargetGroupOpt));
        courseService.createCourse(course1); // We no longer store the returned ID

        // Course 2 (will likely have ID 2L)
        CourseRequest course2 = new CourseRequest();
        course2.setTitle("Coping with Stress");
        course2.setDescription("Recognizing and managing everyday stress");
        course2.setStatus(CourseStatus.PUBLISHED);
        course2.setStartDate(LocalDate.now());
        course2.setEndDate(LocalDate.now().plusWeeks(6));
        course2.setTargetGroups(Set.of(studentTargetGroupOpt, teacherTargetGroupOpt));
        courseService.createCourse(course2);

        // Course 3 (will likely have ID 3L)
        CourseRequest course3 = new CourseRequest();
        course3.setTitle("Building Emotional Resilience");
        course3.setDescription("Advanced techniques for emotional strength.");
        course3.setStatus(CourseStatus.PUBLISHED);
        course3.setStartDate(LocalDate.now().plusWeeks(1));
        course3.setEndDate(LocalDate.now().plusWeeks(10));
        course3.setTargetGroups(Set.of(studentTargetGroupOpt, teacherTargetGroupOpt,  guardianTargetGroupOpt));
        courseService.createCourse(course3);

        // Course 4 (will likely have ID 4L)
        CourseRequest course4 = new CourseRequest();
        course4.setTitle("Parenting with Purpose");
        course4.setDescription("Guidance for guardians on supporting child development.");
        course4.setStatus(CourseStatus.DRAFT);
        course4.setStartDate(LocalDate.now().plusWeeks(2));
        course4.setEndDate(LocalDate.now().plusWeeks(8));
        course4.setTargetGroups(Set.of(teacherTargetGroupOpt, guardianTargetGroupOpt));
        courseService.createCourse(course4);

        log.info("Created 4 courses with assumed IDs 1L, 2L, 3L, 4L.");
    }

    /**
     * Adds lessons to the courses using hardcoded IDs.
     * WARNING: This assumes specific auto-generated IDs (1L, 2L, 3L, 4L) for courses.
     */
    private void courseLessonInit() {
        Long course1Id = 1L;
        Long course2Id = 2L;
        Long course3Id = 3L;
        Long course4Id = 4L;

        // Lessons for Course 1
        courseService.addLessonToCourse(course1Id, new CourseLessonRequest("What is Mental Health?", "Reading", "Mental health includes our emotional, psychological, and social well-being.", 1, null));
        courseService.addLessonToCourse(course1Id, new CourseLessonRequest("Stress and Its Impact", "Video", "https://video-url.com/stress-impact", 2, null));
        courseService.addLessonToCourse(course1Id, new CourseLessonRequest("Mindfulness Basics", "Text", "Learn simple mindfulness techniques.", 3, null));

        // Lessons for Course 2
        courseService.addLessonToCourse(course2Id, new CourseLessonRequest("Basic Coping Mechanisms", "Text", "Learn simple techniques to manage daily stress.", 1, null));
        courseService.addLessonToCourse(course2Id, new CourseLessonRequest("Advanced Coping Strategies", "Video", "https://video-url.com/advanced-coping", 2, null));

        // Lessons for Course 3
        courseService.addLessonToCourse(course3Id, new CourseLessonRequest("Foundations of Resilience", "Reading", "Understanding what makes us resilient.", 1, null));
        courseService.addLessonToCourse(course3Id, new CourseLessonRequest("Building Emotional Strength", "Audio", "Audio guide for emotional strength.", 2, null));
        courseService.addLessonToCourse(course3Id, new CourseLessonRequest("Practicing Gratitude", "Text", "Simple exercises for cultivating gratitude.", 3, null));

        // Lessons for Course 4
        courseService.addLessonToCourse(course4Id, new CourseLessonRequest("Understanding Child Psychology", "Reading", "An introduction to child psychological development.", 1, null));
        courseService.addLessonToCourse(course4Id, new CourseLessonRequest("Effective Communication with Teens", "Video", "Tips for fostering open communication.", 2, null));

        log.info("Initialized Course Lessons using hardcoded IDs.");
    }


    /**
     * Enrolls users into various courses using hardcoded IDs.
     * WARNING: This assumes specific auto-generated IDs (1L, 2L, 3L, 4L) for courses.
     */
    private void courseEnrollmentInit() {
        // User IDs based on creation order in userInit
        Long userId1_ulises = 1L;
        Long userId2_sammy = 2L;
        Long userId3_yorker = 3L;
        Long userId4_napppie = 4L;

        // Using assumed hardcoded IDs
        Long course1Id = 1L;
        Long course2Id = 2L;
        Long course3Id = 3L;
        Long course4Id = 4L;

        // Enrollments
        courseService.enroll(new CourseEnrollmentRequest(course1Id, userId1_ulises));
        courseService.enroll(new CourseEnrollmentRequest(course1Id, userId2_sammy));
        courseService.enroll(new CourseEnrollmentRequest(course1Id, userId3_yorker));

        courseService.enroll(new CourseEnrollmentRequest(course2Id, userId1_ulises));
        courseService.enroll(new CourseEnrollmentRequest(course2Id, userId2_sammy));
        courseService.enroll(new CourseEnrollmentRequest(course2Id, userId4_napppie));

        courseService.enroll(new CourseEnrollmentRequest(course3Id, userId1_ulises));
        courseService.enroll(new CourseEnrollmentRequest(course3Id, userId3_yorker));
        courseService.enroll(new CourseEnrollmentRequest(course3Id, userId4_napppie));

        courseService.enroll(new CourseEnrollmentRequest(course4Id, userId2_sammy));
        courseService.enroll(new CourseEnrollmentRequest(course4Id, userId4_napppie));

        log.info("Initialized Course Enrollments using hardcoded IDs.");
    }

    /**
     * Adds lesson progress for various enrollments.
     * Uses hardcoded course IDs but dynamically retrieves lesson IDs for robustness.
     * WARNING: This assumes specific auto-generated IDs (1L, 2L, 3L, 4L) for courses.
     */

    private void lessonProgressInit() {
        Long userId1 = 1L;
        Long userId2 = 2L;
        Long userId3 = 3L;
        Long userId4 = 4L;

        // Using assumed hardcoded IDs for courses
        List<Long> allCourseIds = List.of(1L, 2L, 3L, 4L);

        // Iterate through courses to get their lessons and then add progress
        for (Long courseId : allCourseIds) {
            // IMPORTANT CHANGE: getLessonsByCourse now returns List<CourseLessonResponse>
            List<CourseLessonResponse> lessons = courseService.getLessonsByCourse(courseId); //
            if (lessons.isEmpty()) {
                log.warn("No lessons found for course ID: {}. Skipping progress initialization for this course.", courseId);
                continue;
            }

            // Map lessons by orderIndex for easy lookup of IDs
            // IMPORTANT CHANGE: Use CourseLessonResponse's methods
            java.util.Map<Integer, Long> lessonIdsByOrder = lessons.stream()
                    .collect(Collectors.toMap(CourseLessonResponse::getOrderIndex, CourseLessonResponse::getId)); //

            // --- Define progress based on course ID and user ID ---
            if (courseId.equals(1L)) { // Course 1: "Introduction to Mental Health"
                // User 1 (Ulises) - partially complete
                if (lessonIdsByOrder.containsKey(1)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId1, courseId), lessonIdsByOrder.get(1), true)); //
                }
                if (lessonIdsByOrder.containsKey(2)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId1, courseId), lessonIdsByOrder.get(2), false)); //
                }

                // User 2 (Sammy) - fully complete
                if (lessonIdsByOrder.containsKey(1)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId2, courseId), lessonIdsByOrder.get(1), true)); //
                }
                if (lessonIdsByOrder.containsKey(2)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId2, courseId), lessonIdsByOrder.get(2), true)); //
                }
                if (lessonIdsByOrder.containsKey(3)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId2, courseId), lessonIdsByOrder.get(3), true)); //
                }

            } else if (courseId.equals(2L)) { // Course 2: "Coping with Stress"
                // User 1 (Ulises) - completed first lesson
                if (lessonIdsByOrder.containsKey(1)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId1, courseId), lessonIdsByOrder.get(1), true)); //
                }

                // User 2 (Sammy) - started but not completed
                if (lessonIdsByOrder.containsKey(1)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId2, courseId), lessonIdsByOrder.get(1), false)); //
                }

            } else if (courseId.equals(3L)) { // Course 3: "Building Emotional Resilience"
                // User 1 (Ulises) - fully complete
                if (lessonIdsByOrder.containsKey(1)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId1, courseId), lessonIdsByOrder.get(1), true)); //
                }
                if (lessonIdsByOrder.containsKey(2)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId1, courseId), lessonIdsByOrder.get(2), true)); //
                }
                if (lessonIdsByOrder.containsKey(3)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId1, courseId), lessonIdsByOrder.get(3), true)); //
                }

                // User 3 (Yorker) - partially complete
                if (lessonIdsByOrder.containsKey(1)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId3, courseId), lessonIdsByOrder.get(1), true)); //
                }
                if (lessonIdsByOrder.containsKey(2)) {
                    courseService.addLessonProgress(new LessonProgressRequest(
                            new CourseEnrollmentId(userId3, courseId), lessonIdsByOrder.get(2), false)); //
                }

            } else if (courseId.equals(4L)) { // Course 4: "Parenting with Purpose" (Draft Course)
                // User 2 (Sammy) - no progress yet
                // User 4 (Napppie) - no progress yet
            }
        }
        log.info("Initialized Lesson Progress using hardcoded IDs.");
    }
}