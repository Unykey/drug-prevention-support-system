package com.swp08.dpss.config;

import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.GuardianCreationRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.requests.course.*;
import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.course.CourseEnrollmentId;
import com.swp08.dpss.enums.*;
import com.swp08.dpss.service.interfaces.UserService;

import com.swp08.dpss.service.interfaces.course.CourseEnrollmentService;
import com.swp08.dpss.service.interfaces.course.CourseLessonService;
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
import java.util.ArrayList;
import java.util.List;


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
        // Guest Self-Registration
        // No Guardian
        UserCreationRequest member1 = new UserCreationRequest();
        member1.setName("Ulises Sieghard");
        member1.setPassword("wP6!4.!o=Z");
        member1.setGender(Genders.MALE);
        member1.setEmail("sieghard@eventbrite.com");
        member1.setPhone("0934567890");
        member1.setDateOfBirth(LocalDate.of(1999, 1, 4));

        UserCreationRequest member2 = new UserCreationRequest();
        member2.setName("Sammy Cudde");
        member2.setPassword("yN6/f=wX");
        member2.setGender(Genders.FEMALE);
        member2.setEmail("samdde@acquirethisname.com");
        member2.setPhone("0935367812");
        member2.setDateOfBirth(LocalDate.of(2004, 6, 2));

        //1 Guardian for each member
        //Create new member first, add guardian later (on profile)
        GuardianCreationRequest guardian1 = new GuardianCreationRequest();
        guardian1.setGuardianName("Madel Mutton");
        guardian1.setGuardianEmail("mamutton@marketwatch.com");
        guardian1.setGuardianPhone("0681507354");

        UserCreationRequest member3 = new UserCreationRequest();
        member3.setName("Yorker Mutton");
        member3.setPassword("lS2~_ma!A77");
        member3.setEmail("ymutton5@acquirethisname.com");
        member3.setPhone("0681507354");
        member3.setDateOfBirth(LocalDate.of(2009, 6, 4));
        member3.setGuardian(guardian1);

        //Create new member and guardian while in register
        GuardianCreationRequest guardian2 = new GuardianCreationRequest();
        guardian2.setGuardianName("Innis Betters");
        guardian2.setGuardianEmail("eriss@marketwatch.com");
        guardian2.setGuardianPhone("0681506475");

        UserCreationRequest member4 = new UserCreationRequest();
        member4.setName("Nappie Rysom");
        member4.setPassword("lS2~_ma!A77");
        member4.setGender(Genders.PREFER_NOT_TO_SAY);
        member4.setEmail("rysom@acquirethisname.com");
        member4.setPhone("0451567354");
        member4.setDateOfBirth(LocalDate.of(2015, 6, 25));
        member4.setGuardian(guardian2);

        userService.register(member1);
        userService.register(member2);
        userService.register(member3);
        userService.register(member4);
        System.out.println("Created User");

        // Admin-Created Accounts
        // Admin role
        AdminUserCreationRequest admin1 = new AdminUserCreationRequest();
        admin1.setName("admin1");
        admin1.setPassword("12345678");
        admin1.setRole(Roles.ADMIN);
        admin1.setDateOfBirth(LocalDate.of(1999, 1, 4));
        admin1.setEmail("admintest@example.com");
        admin1.setPhone("260-993-0884");
        admin1.setStatus(User_Status.VERIFIED);

        // Staff role
        AdminUserCreationRequest staff1 = new AdminUserCreationRequest();
        staff1.setName("staff1");
        staff1.setPassword("qweasdzxc");
        staff1.setRole(Roles.STAFF);
        staff1.setGender(Genders.MALE);
        staff1.setDateOfBirth(LocalDate.of(1980, 1, 4));
        staff1.setEmail("staff1@example.com");
        staff1.setPhone("0934566542");
        staff1.setStatus(User_Status.VERIFIED);

        AdminUserCreationRequest staff2 = new AdminUserCreationRequest();
        staff2.setName("staff1");
        staff2.setPassword("qweasdzxc");
        staff2.setRole(Roles.STAFF);
        staff2.setGender(Genders.NON_BINARY);
        staff2.setDateOfBirth(LocalDate.of(1980, 1, 4));
        staff2.setEmail("staff2@example.com");
        staff2.setPhone("0932366542");
        staff2.setStatus(User_Status.LOCKED);

        // Consultant role
        AdminUserCreationRequest consultant1 = new AdminUserCreationRequest();
        consultant1.setName("consultant1");
        consultant1.setPassword("cV8#U}4@<");
        consultant1.setRole(Roles.CONSULTANT);
        consultant1.setDateOfBirth(LocalDate.of(1980, 1, 4));
        consultant1.setEmail("consultant1@example.com");
        consultant1.setPhone("094232342");

        // Manager role
        AdminUserCreationRequest manager1 = new AdminUserCreationRequest();
        manager1.setName("manager1");
        manager1.setPassword("manager123");
        manager1.setRole(Roles.MANAGER);
        manager1.setDateOfBirth(LocalDate.of(1980, 1, 6));
        manager1.setEmail("manager@gmail.com");
        manager1.setPhone("0123675432");

        userService.createNewUser(admin1);
        userService.createNewUser(staff1);
        userService.createNewUser(staff2);
        userService.createNewUser(manager1);
        userService.createNewUser(consultant1);
        System.out.println("Created Admin User");

        try {
            surveyInit();
            log.info("Survey initialization complete.");
        } catch (Exception e) {
            e.printStackTrace(); // good for pinpointing which line failed
            log.error("Survey initialization failed:" + e.getMessage());
        }

        try {
            answerInit();
            log.info("Survey Answer initialization complete.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Survey Answer initialization failed: " + e.getMessage());
        }

        try {
            courseInit();
            log.info("Course initialization complete.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Course initialization failed: " + e.getMessage());
        }

        try{
            courseEnrollmentInit();
            log.info("CourseEnrollment initialization complete.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CourseEnrollment initialization failed: " + e.getMessage());
        }

        try {
            courseLessonInit();
            log.info("CourseLesson initialization complete.");
        } catch(Exception e){
            e.printStackTrace();
            log.error("CourseLesson initialization failed: " + e.getMessage());
        }

        try {
            lessonProgressInit();
            log.info("Lesson Progress initialization complete.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Lesson Progress initialization failed: " + e.getMessage());
        }


        log.info("DataInitializer finished.");
    }

    private void surveyInit() {
        SurveyDetailsDto assist = surveyService.createSurvey(
                new CreateSurveyRequest("ASSIST", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Đánh giá mức độ sử dụng chất gây nghiện"));

        SurveyDetailsDto crafft = surveyService.createSurvey(
                new CreateSurveyRequest("CRAFFT", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Sàng lọc nguy cơ sử dụng chất gây nghiện"));

        SurveyDetailsDto dast10 = surveyService.createSurvey(
                new CreateSurveyRequest("DAST-10", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Khảo sát mức độ nghiện các loại ma túy"));

        List<String> options = List.of("Có", "Không");

        surveyQuestionService.addQuestionToSurvey(assist.getId(),
                new SurveyQuestionRequest("Bạn đã từng sử dụng rượu chưa?", QuestionTypes.YN, "Có", options));

        surveyQuestionService.addQuestionToSurvey(assist.getId(),
                new SurveyQuestionRequest("Bạn có hút thuốc lá không?", QuestionTypes.YN, "Không", options));

        surveyQuestionService.addQuestionToSurvey(crafft.getId(),
                new SurveyQuestionRequest("Bạn từng đi xe có người dùng chất?", QuestionTypes.YN, "Có", options));

        surveyQuestionService.addQuestionToSurvey(crafft.getId(),
                new SurveyQuestionRequest("Bạn có muốn giảm sử dụng chất đó không?", QuestionTypes.YN, "Có", options));

        surveyQuestionService.addQuestionToSurvey(dast10.getId(),
                new SurveyQuestionRequest("Bạn dùng heroin 30 ngày qua?", QuestionTypes.YN, "Không", options));

        surveyQuestionService.addQuestionToSurvey(dast10.getId(),
                new SurveyQuestionRequest("Bạn thấy khó chịu nếu không dùng chất?", QuestionTypes.YN, "Có", options));
    }


    private void answerInit() {
        SurveyDetailsDto survey = surveyService.createSurvey(
                new CreateSurveyRequest("DEMO_SURVEY", SurveyTypes.QUIZ, SurveyStatus.PUBLISHED, "Demo survey for testing answers"));
        Long surveyId = survey.getId();

        List<String> options = List.of("Có", "Không");

        SurveyQuestionDto q1 = surveyQuestionService.addQuestionToSurvey(surveyId,
                new SurveyQuestionRequest("Bạn có uống rượu không?", QuestionTypes.YN, "Có", options));

        SurveyQuestionDto q2 = surveyQuestionService.addQuestionToSurvey(surveyId,
                new SurveyQuestionRequest("Bạn có hút thuốc không?", QuestionTypes.YN, "Không", options));

        SubmitSurveyAnswerRequest answer1 = new SubmitSurveyAnswerRequest();
        answer1.setUserId(1L);
        answer1.setContent("Có");

        SubmitSurveyAnswerRequest answer2 = new SubmitSurveyAnswerRequest();
        answer2.setUserId(1L);
        answer2.setContent("Không");

        surveyAnswerService.submitAnswer(surveyId, q1.getId(), answer1);
        surveyAnswerService.submitAnswer(surveyId, q2.getId(), answer2);
    }

    private void courseInit() {
        CourseRequest course1 = new CourseRequest();
        course1.setTitle("Introduction to Mental Health");
        course1.setDescription("A course about understanding mental well-being");
        course1.setStatus(CourseStatus.PUBLISHED);
        course1.setTargetGroups(List.of("Teens", "Young Adults"));
        course1.setStartDate(LocalDate.now());
        course1.setEndDate(LocalDate.now().plusWeeks(4));

        CourseRequest course2 = new CourseRequest();
        course2.setTitle("Coping with Stress");
        course2.setDescription("Recognizing and managing everyday stress");
        course2.setStatus(CourseStatus.PUBLISHED);
        course2.setTargetGroups(List.of("Adults"));
        course2.setStartDate(LocalDate.now());
        course2.setEndDate(LocalDate.now().plusWeeks(6));

        courseService.createCourse(course1);
        courseService.createCourse(course2);

        System.out.println("Initialized Courses");
    }

    private void courseLessonInit() {
        CourseLessonRequest lesson1 = new CourseLessonRequest();
        lesson1.setTitle("What is Mental Health?");
        lesson1.setType("READING");
        lesson1.setContent("Mental health includes our emotional, psychological, and social well-being.");
        lesson1.setOrderIndex(1);

        CourseLessonRequest lesson2 = new CourseLessonRequest();
        lesson2.setTitle("Stress and Its Impact");
        lesson2.setType("VIDEO");
        lesson2.setContent("https://video-url.com/stress-impact");
        lesson2.setOrderIndex(2);

        // Assuming course ID = 1 exists
        courseService.addLessonToCourse(1L, lesson1);
        courseService.addLessonToCourse(1L, lesson2);

        System.out.println("Initialized Course Lessons");
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

        System.out.println("Initialized Course Enrollments");
    }

    private void lessonProgressInit() {
        LessonProgressRequest progress1 = new LessonProgressRequest();

        progress1.setEnrollmentId(new CourseEnrollmentId(1L,1L));
        progress1.setLessonId(1L);
        progress1.setCompleted(true);

        LessonProgressRequest progress2 = new LessonProgressRequest();
        progress2.setEnrollmentId(new CourseEnrollmentId(1L,1L));
        progress2.setLessonId(2L);
        progress2.setCompleted(false);

        courseService.addLessonProgress(progress1);
        courseService.addLessonProgress(progress2);

        System.out.println("Initialized Lesson Progress");
    }


}
