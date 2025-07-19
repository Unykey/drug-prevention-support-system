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
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.course.CourseEnrollmentId;
import com.swp08.dpss.enums.*;
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

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList; // Added for creating lists

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
        try {
            surveyInit();
            log.info("Survey initialization complete.");
        } catch (Exception e) {
            log.error("Survey initialization failed:", e);
        }

        // Add Survey Answer
        try {
            answerInit();
            log.info("Survey Answer initialization complete.");
        } catch (Exception e) {
            log.error("Survey Answer initialization failed: ", e);
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

        userService.createNewUser(cons1);
        userService.createNewUser(cons2);
        userService.createNewUser(cons3);
        userService.createNewUser(staff1);
        userService.createNewUser(staff2);
        userService.createNewUser(man1);
        userService.createNewUser(ad1);
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

    private void courseInit() {
        CourseRequest course1 = new CourseRequest();
        course1.setTitle("Introduction to Mental Health");
        course1.setDescription("A course about understanding mental well-being");
        course1.setStatus(CourseStatus.PUBLISHED);
        course1.setStartDate(LocalDate.now());
        course1.setEndDate(LocalDate.now().plusWeeks(4));

        CourseRequest course2 = new CourseRequest();
        course2.setTitle("Coping with Stress");
        course2.setDescription("Recognizing and managing everyday stress");
        course2.setStatus(CourseStatus.PUBLISHED);
        course2.setStartDate(LocalDate.now());
        course2.setEndDate(LocalDate.now().plusWeeks(6));

        courseService.createCourse(course1);
        courseService.createCourse(course2);

        log.info("Initialized Courses");
    }

    private void courseLessonInit() {
        CourseLessonRequest lesson1 = new CourseLessonRequest();
        lesson1.setTitle("What is Mental Health?");
        lesson1.setType("Reading"); // Assuming CourseLessonType enum, or a String field
        lesson1.setContent("Mental health includes our emotional, psychological, and social well-being.");
        lesson1.setOrderIndex(1);

        CourseLessonRequest lesson2 = new CourseLessonRequest();
        lesson2.setTitle("Stress and Its Impact");
        lesson2.setType("Video"); // Assuming CourseLessonType enum, or a String field
        lesson2.setContent("https://video-url.com/stress-impact");
        lesson2.setOrderIndex(2);

        // Assuming course ID = 1 exists
        courseService.addLessonToCourse(1L, lesson1);
        courseService.addLessonToCourse(1L, lesson2);

        log.info("Initialized Course Lessons");
    }

    private void courseEnrollmentInit() {
        CourseEnrollmentRequest enroll1 = new CourseEnrollmentRequest();
        enroll1.setCourseId(1L);
        enroll1.setUserId(1L);

        CourseEnrollmentRequest enroll2 = new CourseEnrollmentRequest();
        enroll2.setCourseId(2L);
        enroll2.setUserId(2L);

        courseService.enroll(enroll1);
        courseService.enroll(enroll2);

        log.info("Initialized Course Enrollments");
    }

    private void lessonProgressInit() {
        LessonProgressRequest progress1 = new LessonProgressRequest();

        progress1.setEnrollmentId(new CourseEnrollmentId(1L, 1L));
        progress1.setLessonId(1L);
        progress1.setCompleted(true);

        LessonProgressRequest progress2 = new LessonProgressRequest();
        progress2.setEnrollmentId(new CourseEnrollmentId(1L, 1L));
        progress2.setLessonId(2L);
        progress2.setCompleted(false);

        courseService.addLessonProgress(progress1);
        courseService.addLessonProgress(progress2);

        log.info("Initialized Lesson Progress");
    }
}