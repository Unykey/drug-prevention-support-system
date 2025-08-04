package com.swp08.dpss.config;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.requests.program.ProgramParticipationRequest;
import com.swp08.dpss.dto.requests.program.ProgramRequest;
import com.swp08.dpss.dto.requests.survey.*;
import com.swp08.dpss.dto.requests.survey.BulkSubmitSurveyAnswerRequest.AnswerSubmission;
import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.GuardianCreationRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.requests.course.*;
import com.swp08.dpss.dto.requests.survey.AnswerOptionRequest;
import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.responses.course.CourseLessonResponse;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.consultant.Availability;
import com.swp08.dpss.entity.consultant.Consultant;
import com.swp08.dpss.entity.consultant.Qualification;
import com.swp08.dpss.entity.consultant.Specialization;
import com.swp08.dpss.entity.course.*;
import com.swp08.dpss.entity.program.Program;
import com.swp08.dpss.entity.program.ProgramSurvey;
import com.swp08.dpss.entity.program.ProgramSurveyId;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.enums.*;
import com.swp08.dpss.repository.PostRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.course.CourseRepository;
import com.swp08.dpss.repository.course.CourseSurveyRepository;
import com.swp08.dpss.repository.course.TargetGroupRepository;
import com.swp08.dpss.repository.program.ProgramRepository;
import com.swp08.dpss.repository.program.ProgramSurveyRepository;
import com.swp08.dpss.repository.survey.AnswerOptionRepository;
import com.swp08.dpss.repository.survey.SurveyQuestionRepository;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.PostService;
import com.swp08.dpss.service.interfaces.UserService;

import com.swp08.dpss.service.interfaces.consultant.ConsultantService;
import com.swp08.dpss.service.interfaces.course.CourseService;
import com.swp08.dpss.service.interfaces.program.ProgramService;
import com.swp08.dpss.service.interfaces.survey.AnswerOptionService;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import com.swp08.dpss.service.interfaces.survey.SurveyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList; // Added for creating lists
import java.util.Set;
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
    private final TargetGroupRepository targetGroupRepository;
    private final UserRepository userRepository;
    private final ProgramService programService;
    private final ConsultantService consultantService;
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final SurveyRepository surveyRepository;
    private final ProgramSurveyRepository programSurveyRepository;
    private final CourseSurveyRepository courseSurveyRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final PostService postService;

    @Override
    public void run(String... args) throws Exception {
        // === User Part ===
        // Add User
        try {
            userInit();
            log.info("=== 1.1 User initialization complete ===");
        } catch (Exception e) {
            log.error("User initialization failed:", e);
        }

        // Add Consultant
        try {
            consultantInit();
            log.info("=== 1.2 Consultant initialization complete ===");
        } catch (Exception e) {
            log.error("Consultant initialization failed:", e);
        }

        // === Course Part ===
        // Add Target Group - Initialize target groups before courses
        try {
            targetGroupInit();
            log.info("=== 2.1 Target Group initialization complete ===");
        } catch (Exception e) {
            log.error("Target Group initialization failed: ", e);
        }

        // Add Course
        try {
            courseInit();
            log.info("=== 2.2 Course initialization complete ===");
        } catch (Exception e) {
            log.error("Course initialization failed: ", e);
        }

        // Add Course Lesson
        try {
            courseLessonInit();
            log.info("=== 2.3 CourseLesson initialization complete ===");
        } catch (Exception e) {
            log.error("CourseLesson initialization failed: ", e);
        }

        // Add Course Enrollment
        try {
            courseEnrollmentInit();
            log.info("=== 2.4 CourseEnrollment initialization complete ===");
        } catch (Exception e) {
            log.error("CourseEnrollment initialization failed: ", e);
        }

        // Add Lesson Progress
        try {
            lessonProgressInit();
            log.info("=== 2.5 Lesson Progress initialization complete ===");
        } catch (Exception e) {
            log.error("Lesson Progress initialization failed: ", e);
        }

        // === Program Part ===
        // Add Program
        try {
            programInit();
            log.info("=== 3.1 Program initialization complete ===");
        } catch (Exception e) {
            log.error("Program initialization failed: ", e);
        }
        // Add Program Participation
        try {
            programParticipationInit();
            log.info("=== 3.2 ProgramParticipation initialization complete ===");
        } catch (Exception e) {
            log.error("ProgramParticipation initialization failed: ", e);
        }

        // === Survey Part ===
        // Add Survey general
        try {
            surveyInit();
            log.info("=== 4.1 Survey initialization complete ===");
        } catch (Exception e) {
            log.error("Survey initialization failed:", e);
        }

        // Add Survey Question (Course)
        //TODO

        // Add Survey Question (Program)
        //TODO

        // Add Survey Answer
        try {
//            answerInit();
            log.info("=== 4.2 Survey Answer initialization complete ===");
        } catch (Exception e) {
            log.error("Survey Answer initialization failed: ", e);
        }

        // === Post Part ===
        try {
            postInit();
            log.info("=== 5.1 Post initialization complete ===");
        } catch (Exception e) {
            log.error("Post initialization failed:", e);
        }

        log.info("===== DataInitializer finished =====");
    }

    private void userInit() {
        // ===== Guest Self-Registration =====
        // No Guardian (>18) - ADULT, STUDENT, TEACHER, GUARDIAN
        UserCreationRequest member1 = new UserCreationRequest("Ulises Sieghard", "wP6!4.!o=Z", Genders.MALE, LocalDate.of(1999, 1, 4), "sieghard@eventbrite.com", "0934567890", null);

        UserCreationRequest member2 = new UserCreationRequest("Sammy Cudde", "yN6/f=wX", Genders.FEMALE, LocalDate.of(2003, 1, 4), "samdde@acquirethisname.com", "0935367812", null);

        // Each member has a guardian (<=18) - CHILD, STUDENT
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
        // Consultant role - ADULT, TEACHER
        AdminUserCreationRequest cons1 = new AdminUserCreationRequest("cons1", "qweasdzxc", Roles.CONSULTANT, Genders.NON_BINARY, LocalDate.of(1980, 1, 4), "cons1@example.com", "094232342", null);

        AdminUserCreationRequest cons2 = new AdminUserCreationRequest("cons2", "qweasdzxc", Roles.CONSULTANT, Genders.MALE, LocalDate.of(2000, 1, 4), "cons2@example.com", "094232123", null);

        AdminUserCreationRequest cons3 = new AdminUserCreationRequest("cons3", "qweasdzxc", Roles.CONSULTANT, null, LocalDate.of(2003, 1, 4), "cons3@example.com", "012232342", null);

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

    @PersistenceContext
    private EntityManager entityManager;

    private void consultantInit() {
        // 1 Find User entity First
        User user1 = userRepository.findByEmail("cons1@example.com").orElseThrow();
        User user2 = userRepository.findByEmail("cons2@example.com").orElseThrow();
        User user3 = userRepository.findByEmail("cons3@example.com").orElseThrow();

        // 2 Availabilities
        Availability avai1 = new Availability(LocalDate.of(2025, 8, 20), LocalTime.of(9, 0), LocalTime.of(11, 0), true);
        Availability avai2 = new Availability(LocalDate.of(2025, 8, 22), LocalTime.of(14, 0), LocalTime.of(16, 0), true);
        Availability avai3 = new Availability(LocalDate.of(2025, 8, 24), LocalTime.of(14, 0), LocalTime.of(16, 0), true);
        Availability avai4 = new Availability(LocalDate.of(2025, 8, 26), LocalTime.of(14, 0), LocalTime.of(16, 0), true);

        // 3 Specializations
        Specialization spec1 = new Specialization("Phòng chống lạm dụng ma túy", "Chuyên về các kỹ thuật can thiệp và tư vấn cai nghiện ma túy");
        Specialization spec2 = new Specialization("Tư vấn tâm lý cho thanh thiếu niên", "Tư vấn tâm lý học đường, phòng ngừa nghiện cho tuổi vị thành niên");
        Specialization spec3 = new Specialization("Phục hồi chức năng sau cai nghiện", "Hỗ trợ tái hòa nhập cộng đồng, chăm sóc sau cai");

        // 4 Qualifications
        Qualification qual1 = new Qualification("Tiến sĩ Y học", "ĐH Y ABC", 2015, "VN123456789", "Bộ Y tế A", "Tâm lý học nghiện");
        Qualification qual2 = new Qualification("Thạc sĩ Tâm lý học", "ĐHQG XYZ", 2018, "HCM987654321", "Bộ Y tế B", "Sức khỏe tâm thần cho thanh thiếu niên");
        Qualification qual3 = new Qualification("Chuyên gia Tư vấn", "ĐH Y Dược 123", 2020, "CNT-003", "Bộ Sức Khỏe", "Phục hồi chức năng");

        // 5 Create Consultant and set all before save
        Consultant cons1 = new Consultant();
        cons1.setUser(user1);
        cons1.setBio("Tiến sĩ Madel Mutton là trưởng khoa Bệnh viện từ năm 2000 - 2019. Trong suốt thời gian tại Bệnh viện Ung thư Quốc gia, ông luôn dành thời gian tâm huyết nghiên cứu, áp dụng điều trị cho nhóm bệnh. Tham gia vào hệ thống bệnh viện, ông ấy làm trưởng đơn vị điều trị hoá chất và hỗ trợ, Trung tâm chấn thương chỉnh hình.");
        cons1.setProfilePicture("https://example.com/avatar1.jpg");
        consultantService.createNewConsultant2(user1.getId(), cons1, Set.of(avai1), Set.of(spec1), qual1);

        Consultant cons2 = new Consultant();
        cons2.setUser(user2);
        cons2.setBio("Ông đã làm việc và nghiên cứu hơn 25 năm trong việc thúc đẩy thực hành tâm lý theo định hướng phục hồi và dựa trên bằng chứng cho những người có vấn đề về sức khỏe tâm thần. Ông đã làm việc tại Úc, Vương quốc Anh, Châu Âu, Châu Mỹở mọi cấp độ bao gồm chăm sóc lâm sàng trực tiếp, nghiên cứu và giảng dạy sau đại học, chính sách quốc gia và phát triển thực hành cũng như vai trò cải thiện dịch vụ lâm sàng.");
        cons2.setProfilePicture("https://example.com/avatar1.jpg");
        consultantService.createNewConsultant2(user2.getId(), cons2, Set.of(avai2), Set.of(spec2), qual2);

        Consultant cons3 = new Consultant();
        cons3.setUser(user3);
        cons3.setBio("Với sự cần mẫn trong học tập, tận tụy trong công việc nhiều năm qua, đến nay ông đã tích lũy được nhiều kinh nghiệm trong khám, điều trị các bệnh lý về chuyên khoa Chấn thương chỉnh hình, đặc biệt là các bệnh liên quan đến thoái hóa xương khớp, cột sống. Thường xuyên tham gia các khóa dào tạo liên tục, các hội nghị cập nhật về chấn thương chỉnh hình, phẫu thuật tạo hình-bàn tay, các khóa cập nhật điều trị gãy xương.");
        cons3.setProfilePicture("https://example.com/avatar1.jpg");
        consultantService.createNewConsultant2(user3.getId(), cons3, Set.of(avai2, avai4), Set.of(spec2), qual3);

        log.info("Created Consultants");
    }

    private void surveyInit() {
        SurveyDetailsDto assist = surveyService.createSurvey(
                new CreateSurveyRequest("ASSIST", CourseSurveyRoles.QUIZ.toString(), SurveyStatus.PUBLISHED, "Đánh giá mức độ sử dụng chất gây nghiện"));

        SurveyDetailsDto crafft = surveyService.createSurvey(
                new CreateSurveyRequest("CRAFFT", CourseSurveyRoles.QUIZ.toString(), SurveyStatus.PUBLISHED, "Sàng lọc nguy cơ sử dụng chất gây nghiện"));

        SurveyDetailsDto dast10 = surveyService.createSurvey(
                new CreateSurveyRequest("DAST-10", CourseSurveyRoles.QUIZ.toString(), SurveyStatus.PUBLISHED, "Khảo sát mức độ nghiện các loại ma túy"));

        AnswerOptionRequest Q1T = new AnswerOptionRequest("Có", true);
        AnswerOptionRequest Q1F = new AnswerOptionRequest("Không", false);
        AnswerOptionRequest Q2T = new AnswerOptionRequest("Có", false);
        AnswerOptionRequest Q2F = new AnswerOptionRequest("Không", true);

        // SurveyQuestionRequest now expects List<AnswerOptionRequest>
        surveyQuestionService.addQuestionToSurvey(crafft.getId(),
                new SurveyQuestionRequest(
                        "Bạn đã từng sử dụng rượu chưa?",
                        QuestionTypes.YN,
                        List.of(
                                new AnswerOptionRequest("Có", true),
                                new AnswerOptionRequest("Không", false)
                        )
                ));

        surveyQuestionService.addQuestionToSurvey(assist.getId(),
                new SurveyQuestionRequest(
                        "Bạn có hút thuốc lá không?",
                        QuestionTypes.YN,
                        List.of(
                                new AnswerOptionRequest("Có", false),
                                new AnswerOptionRequest("Không", true)
                        )
                ));

        surveyQuestionService.addQuestionToSurvey(crafft.getId(),
                new SurveyQuestionRequest(
                        "Bạn từng đi xe có người dùng chất ma túy?",
                        QuestionTypes.YN,
                        List.of(
                                new AnswerOptionRequest("Có", true),
                                new AnswerOptionRequest("Không", false)
                        )
                ));

        surveyQuestionService.addQuestionToSurvey(assist.getId(),
                new SurveyQuestionRequest(
                        "Bạn có muốn giảm sử dụng rượu bia không?",
                        QuestionTypes.YN,
                        List.of(
                                new AnswerOptionRequest("Có", true),
                                new AnswerOptionRequest("Không", false)
                        )
                ));

        surveyQuestionService.addQuestionToSurvey(dast10.getId(),
                new SurveyQuestionRequest(
                        "Bạn dùng heroin 30 ngày qua?",
                        QuestionTypes.YN,
                        List.of(
                                new AnswerOptionRequest("Có", false),
                                new AnswerOptionRequest("Không", true)
                        )
                ));

        surveyQuestionService.addQuestionToSurvey(dast10.getId(),
                new SurveyQuestionRequest(
                        "Bạn thấy khó chịu nếu không dùng ma túy?",
                        QuestionTypes.YN,
                        List.of(
                                new AnswerOptionRequest("Có", true),
                                new AnswerOptionRequest("Không", false)
                        )
                ));
    }

    private void programSurveyInit() {
        SurveyDetailsDto preFeedbackSurvey = surveyService.createSurvey(
                new CreateSurveyRequest("Đánh giá trước chương trình", ProgramSurveyRoles.PRE_FEEDBACK.toString(), SurveyStatus.PUBLISHED, "Khảo sát nhằm đánh giá hiểu biết trước khi tham gia chương trình."));

        SurveyDetailsDto postFeedbackSurvey = surveyService.createSurvey(
                new CreateSurveyRequest("Đánh giá sau chương trình", ProgramSurveyRoles.POST_FEEDBACK.toString(), SurveyStatus.PUBLISHED, "Khảo sát để đánh giá hiệu quả sau khi hoàn thành chương trình."));

        SurveyDetailsDto FeedbackSurvey  = surveyService.createSurvey(
                new CreateSurveyRequest("Phản hồi chương trình", ProgramSurveyRoles.FEEDBACK.toString(), SurveyStatus.PUBLISHED, "Khảo sát để đánh giá hiệu quả sau khi hoàn thành chương trình."));
    }

    private void linkSurveyToProgramsAndCourses() {
        // 1. Find program and course (assumed already in DB)
        Program p = programRepository.findByTitle("Chương trình Tư vấn Phòng chống Ma túy cho Thanh thiếu niên").orElseThrow();
        Course c = courseRepository.findByTitle("Nhận thức Cơ bản về Ma túy").orElseThrow();

        // 2. Find surveys (created from surveyInit)
        Survey assist = surveyRepository.findByName("ASSIST").orElseThrow();
        Survey crafft = surveyRepository.findByName("CRAFFT").orElseThrow();
        Survey preFeedbackSurvey = surveyRepository.findByName("Đánh giá trước chương trình").orElseThrow();

        // 3. Link to program
        programSurveyRepository.save(new ProgramSurvey(
                new ProgramSurveyId(p.getId(), preFeedbackSurvey.getId()), p, preFeedbackSurvey, ProgramSurveyRoles.PRE_FEEDBACK));

        // 4. Link to course
        courseSurveyRepository.save(new CourseSurvey(
                new CourseSurveyId(c.getId(), assist.getId()), c, assist, CourseSurveyRoles.QUIZ));

        courseSurveyRepository.save(new CourseSurvey(
                new CourseSurveyId(c.getId(), crafft.getId()), c, crafft, CourseSurveyRoles.QUIZ));
    }

    private void answerInit() {
        Survey survey = surveyRepository.findByName("CRAFFT").orElseThrow();
        Long surveyId = survey.getId();

        SurveyQuestion question1 = surveyQuestionRepository.findByQuestion("Bạn đã từng sử dụng rượu chưa?").orElseThrow();
        SurveyQuestion question2 = surveyQuestionRepository.findByQuestion("Bạn từng đi xe có người dùng chất ma túy?").orElseThrow();

        String user1Email = "sieghard@eventbrite.com"; // Get email from your userInit()

        // Submit individual answers for testing the single submitAnswer method
        SubmitSurveyAnswerRequest answer1 = new SubmitSurveyAnswerRequest("Có"); // content for q1
        SubmitSurveyAnswerRequest answer2 = new SubmitSurveyAnswerRequest("Không"); // content for q2

        System.out.println("Submit content: " + answer1.getContent());
        System.out.println("Submit content: " + answer2.getContent());
        surveyAnswerService.submitAnswer(surveyId, question1.getQuestion_id(), answer1, user1Email);
        surveyAnswerService.submitAnswer(surveyId, question2.getQuestion_id(), answer2, user1Email);

        // --- Demonstrate Bulk Answer Submission ---
        String user2Email = "samdde@acquirethisname.com"; // Another user

        // Create a BulkSubmitSurveyAnswerRequest
        BulkSubmitSurveyAnswerRequest bulkRequest = new BulkSubmitSurveyAnswerRequest();
        bulkRequest.setSurveyId(surveyId);
        // Assuming userId will be determined from userEmail in service for security
        // bulkRequest.setUserId(2L); // This field might be redundant if user is derived from principal

        List<AnswerSubmission> bulkAnswers = new ArrayList<>();
        // Answer for q1 (user2 answers "Không")
        bulkAnswers.add(new AnswerSubmission(question1.getQuestion_id(), "Không"));
        // Answer for q2 (user2 answers "Có")
        bulkAnswers.add(new AnswerSubmission(question2.getQuestion_id(), "Có"));

        bulkRequest.setAnswers(bulkAnswers);

        // Submit all answers in bulk
        log.info("Submitting bulk answers for user: {}", user2Email);
        surveyAnswerService.submitAllAnswers(surveyId, bulkRequest, user2Email);
        log.info("Bulk answers submission complete for user: {}", user2Email);
    }

    private void targetGroupInit() {
        TargetGroup tg1 = new TargetGroup(TargetGroupName.CHILD, "Trẻ em, thanh thiếu niên (<18)");
        TargetGroup tg2 = new TargetGroup(TargetGroupName.ADULT, "Người trưởng thành (>=18)");
        TargetGroup tg3 = new TargetGroup(TargetGroupName.STUDENT, "Học sinh");
        TargetGroup tg4 = new TargetGroup(TargetGroupName.TEACHER, "Giáo viên");
        TargetGroup tg5 = new TargetGroup(TargetGroupName.GUARDIAN, "Người giám hộ, phụ huynh");

        targetGroupRepository.save(tg1);
        targetGroupRepository.save(tg2);
        targetGroupRepository.save(tg3);
        targetGroupRepository.save(tg4);
        targetGroupRepository.save(tg5);

        log.info("Initialized Target Groups:");
    }

    private void courseInit() {
        TargetGroup childTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.CHILD).orElseThrow(() -> new RuntimeException("Target group Student not found"));
        TargetGroup adultTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.ADULT).orElseThrow(() -> new RuntimeException("Target group Student not found"));
        TargetGroup studentTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.STUDENT).orElseThrow(() -> new RuntimeException("Target group Student not found"));
        TargetGroup teacherTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.TEACHER).orElseThrow(() -> new RuntimeException("Target group Teacher not found"));
        TargetGroup guardianTargetGroupOpt = targetGroupRepository.findByTargetGroupName(TargetGroupName.GUARDIAN).orElseThrow(() -> new RuntimeException("Target group Guardian not found"));

        // Course 1
        CourseRequest course1 = new CourseRequest();
        course1.setTitle("Hậu quả Pháp lý và Xã hội của Ma túy");
        course1.setDescription("Tìm hiểu về quy định pháp luật và những hệ lụy xã hội khi liên quan đến ma túy.");
        course1.setStatus(CourseStatus.ARCHIVED);
        course1.setTargetGroups(Set.of(
                studentTargetGroupOpt,
                adultTargetGroupOpt
        ));
        course1.setStartDate(LocalDate.now().minusWeeks(7));
        course1.setEndDate(LocalDate.now().minusWeeks(2));

        // Course 2
        CourseRequest course2 = new CourseRequest();
        course2.setTitle("Nhận thức Cơ bản về Ma túy");
        course2.setDescription("Giới thiệu các loại ma túy phổ biến, cách nhận biết và hậu quả khi sử dụng.");
        course2.setStatus(CourseStatus.PUBLISHED);
        course2.setTargetGroups(Set.of(
                studentTargetGroupOpt,
                adultTargetGroupOpt
        ));
        course2.setStartDate(LocalDate.now());
        course2.setEndDate(LocalDate.now().plusWeeks(6));

        // Course 3
        CourseRequest course3 = new CourseRequest();
        course3.setTitle("Kỹ năng Từ chối và Ứng phó với Áp lực");
        course3.setDescription("Trang bị kỹ năng từ chối ma túy, ứng phó với áp lực từ bạn bè hoặc môi trường xung quanh.");
        course3.setStatus(CourseStatus.PUBLISHED);
        course3.setTargetGroups(Set.of(
                childTargetGroupOpt,
                studentTargetGroupOpt,
                adultTargetGroupOpt
        ));
        course3.setStartDate(LocalDate.now());
        course3.setEndDate(LocalDate.now().plusWeeks(6));

        // Course 4
        CourseRequest course4 = new CourseRequest();
        course4.setTitle("Nhận thức về Ma túy cho Trẻ em");
        course4.setDescription("Nội dung đơn giản, trực quan giúp trẻ em hiểu được tác hại của ma túy và hình thành thói quen sống lành mạnh.");
        course4.setStatus(CourseStatus.PUBLISHED);
        course4.setTargetGroups(Set.of(
                childTargetGroupOpt,
                adultTargetGroupOpt
        ));
        course4.setStartDate(LocalDate.now());
        course4.setEndDate(LocalDate.now().plusWeeks(4));

        // Course 5
        CourseRequest course5 = new CourseRequest();
        course5.setTitle("Hướng dẫn Giáo viên về Phòng chống Ma túy trong Trường học");
        course5.setDescription("Cung cấp kiến thức và công cụ cho giáo viên để phát hiện, can thiệp và giáo dục học sinh về phòng ngừa ma túy.");
        course5.setStatus(CourseStatus.PUBLISHED);
        course5.setTargetGroups(Set.of(
                teacherTargetGroupOpt
        ));
        course5.setStartDate(LocalDate.now().plusWeeks(1));
        course5.setEndDate(LocalDate.now().plusWeeks(10));

        // Course 6
        CourseRequest course6 = new CourseRequest();
        course6.setTitle("Đồng hành cùng Con trong Phòng ngừa Ma túy");
        course6.setDescription("Hướng dẫn phụ huynh và người giám hộ cách giao tiếp với trẻ, nhận biết dấu hiệu nguy cơ và hỗ trợ con tránh xa ma túy.");
        course6.setStatus(CourseStatus.DRAFT);
        course6.setTargetGroups(Set.of(
                adultTargetGroupOpt,
                guardianTargetGroupOpt
        ));
        course6.setStartDate(LocalDate.now().plusWeeks(2));
        course6.setEndDate(LocalDate.now().plusWeeks(8));

        courseService.createCourse(course1);
        courseService.createCourse(course2);
        courseService.createCourse(course3);
        courseService.createCourse(course4);
        courseService.createCourse(course5);
        courseService.createCourse(course6);

        log.info("Initialized Courses");
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

    private void programInit() {
        Consultant consId1 = userRepository.findByEmail("cons1@example.com").get().getConsultant();
        Consultant consId2 = userRepository.findByEmail("cons2@example.com").get().getConsultant();
        Consultant consId3 = userRepository.findByEmail("cons3@example.com").get().getConsultant();

        User st1 = userRepository.findByEmail("staff1@example.com").get();
        User st2 = userRepository.findByEmail("staff2@example.com").get();
        User mg1 = userRepository.findByEmail("man1@example.com").get();

        programService.addProgram(new ProgramRequest(
                "Chương trình Tư vấn Phòng chống Ma túy cho Thanh thiếu niên",
                "Chương trình nhằm cung cấp kiến thức và kỹ năng cho thanh thiếu niên để phòng ngừa nguy cơ sử dụng ma túy.",
                Set.of(consId1),
                LocalDateTime.of(2025, 8, 15, 10, 0),
                LocalDateTime.of(2025, 8, 15, 12, 0),
                "https://meeting-link-1" // hoặc null nếu chưa có
        ));

        programService.addProgram(new ProgramRequest(
                "Hội thảo Hướng dẫn Phụ huynh Phòng ngừa Ma túy cho Học sinh, sinh viên",
                "Chương trình giáo dục nhằm giúp học sinh, sinh viên nhận diện và từ chối các hành vi liên quan đến ma túy.",
                Set.of(consId2),
                LocalDateTime.of(2025, 8, 15, 15, 0),
                LocalDateTime.of(2025, 8, 15, 16, 30),
                "https://meeting-link-2"
        ));

        programService.addProgram(new ProgramRequest(
                "Chương trình Hỗ trợ Tâm lý và Tái Hòa nhập Cộng đồng",
                "Chương trình hỗ trợ người từng sử dụng ma túy xây dựng lại cuộc sống, vượt qua mặc cảm và nguy cơ tái nghiện.",
                Set.of(consId3),
                LocalDateTime.of(2025, 8, 20, 8, 30),
                LocalDateTime.of(2025, 8, 20, 9, 30),
                "https://meeting-link-3"
        ));

        programService.addProgram(new ProgramRequest(
                "Khóa học Trực tuyến: Nhận biết và Hành động sớm",
                "Khóa học online giúp người dân nhận biết sớm dấu hiệu sử dụng ma túy và cách ứng phó.",
                Set.of(consId1, consId2),
                LocalDateTime.of(2025, 8, 20, 16, 0),
                LocalDateTime.of(2025, 8, 20, 17, 0),
                "https://meeting-link-4"
        ));

        programService.addProgram(new ProgramRequest(
                "Sự kiện Giao lưu – Nói Không với Ma túy",
                "Chương trình giao lưu, chia sẻ trải nghiệm và truyền cảm hứng sống tích cực.",
                Set.of(consId1, consId3),
                LocalDateTime.of(2025, 9, 2, 10, 0),
                LocalDateTime.of(2025, 9, 2, 12, 0),
                "https://meeting-link-5"
        ));

        programService.addProgram(new ProgramRequest(
                "Tọa đàm: Vai trò của Gia đình trong Việc Phòng ngừa Ma túy",
                "Thảo luận về vai trò cha mẹ và người thân trong gia đình trong việc phòng ngừa ma túy.",
                Set.of(consId1, consId2, consId3),
                LocalDateTime.of(2025, 9, 6, 10, 0),
                LocalDateTime.of(2025, 9, 6, 12, 0),
                "https://meeting-link-6"
        ));
        log.info("Initialized Programs");
    }

    private void programParticipationInit() {
        // User IDs based on creation order in userInit
        // Member
        Long userId1_ulises = 1L;
        Long userId2_sammy = 2L;
        Long userId3_yorker = 3L;
        Long userId4_napppie = 4L;

        // Staff, Manager
        Long st1 = 8L;
        Long st2 = 9L;
        Long mg1 = 10L;

        // Using assumed hardcoded IDs for programs
        Long program1Id = 1L;
        Long program2Id = 2L;
        Long program3Id = 3L;
        Long program4Id = 4L;
        Long program5Id = 5L;
        Long program6Id = 6L;

        // Participation for each program
        programService.enroll(new ProgramParticipationRequest(program1Id, userId1_ulises));
        programService.enroll(new ProgramParticipationRequest(program1Id, userId2_sammy));
        programService.enroll(new ProgramParticipationRequest(program1Id, userId3_yorker));

        programService.enroll(new ProgramParticipationRequest(program2Id, userId1_ulises));
        programService.enroll(new ProgramParticipationRequest(program2Id, userId3_yorker));

        programService.enroll(new ProgramParticipationRequest(program3Id, userId2_sammy));
        programService.enroll(new ProgramParticipationRequest(program3Id, userId3_yorker));

        programService.enroll(new ProgramParticipationRequest(program4Id, userId2_sammy));

        programService.enroll(new ProgramParticipationRequest(program5Id, userId1_ulises));

        programService.enroll(new ProgramParticipationRequest(program6Id, userId3_yorker));

        log.info("Initialized ProgramParticipation using hardcoded IDs.");
    }

    private void surveyQuestionCourseInit() {

    }

    private void postInit() {
        // Find users to assign as post authors
        User staffUser = userRepository.findByEmail("staff1@example.com").orElseThrow(() -> new RuntimeException("Staff user not found for post initialization."));
        User managerUser = userRepository.findByEmail("man1@example.com").orElseThrow(() -> new RuntimeException("Manager user not found for post initialization."));
        User memberUser = userRepository.findByEmail("sieghard@eventbrite.com").orElseThrow(() -> new RuntimeException("Member user not found for post initialization."));

        // Create a DRAFT post
        PostRequest draftPostRequest = new PostRequest();
        draftPostRequest.setTitle("Draft Post: A draft on a new topic");
        draftPostRequest.setContent("This is a draft of a post that is not yet ready for publication. It is a work in progress.");
        draftPostRequest.setAuthorId(staffUser.getId());
        draftPostRequest.setStatus(PostStatus.DRAFT);
        postService.createPost(draftPostRequest);

        // Create a PUBLISHED post by a Staff member
        PostRequest publishedPostRequest1 = new PostRequest();
        publishedPostRequest1.setTitle("First Official Post: Welcome to Our Platform");
        publishedPostRequest1.setContent("We are excited to launch our new community platform! This post outlines our goals and vision.");
        publishedPostRequest1.setAuthorId(staffUser.getId());
        publishedPostRequest1.setStatus(PostStatus.PUBLISHED);
        postService.createPost(publishedPostRequest1);

        // Create another PUBLISHED post by a Manager
        PostRequest publishedPostRequest2 = new PostRequest();
        publishedPostRequest2.setTitle("Upcoming Events and Webinars");
        publishedPostRequest2.setContent("Check out our calendar for a list of upcoming events and webinars. We have many exciting things planned!");
        publishedPostRequest2.setAuthorId(managerUser.getId());
        publishedPostRequest2.setStatus(PostStatus.PUBLISHED);
        postService.createPost(publishedPostRequest2);

        // Create a post by a regular Member
        PostRequest memberPostRequest = new PostRequest();
        memberPostRequest.setTitle("My Experience with the Course");
        memberPostRequest.setContent("I recently completed the 'Nhận thức Cơ bản về Ma túy' course and wanted to share my positive experience.");
        memberPostRequest.setAuthorId(memberUser.getId());
        memberPostRequest.setStatus(PostStatus.PUBLISHED);
        postService.createPost(memberPostRequest);

        // Create a DRAFT post by a Staff member
        PostRequest publishedPostRequest4 = new PostRequest();
        publishedPostRequest4.setTitle("Common misconception");
        publishedPostRequest4.setContent("In our modern, it's understandable that some people haven't contacted these drugs once. Therefore, they will be vulnerable if they aren't prepared");
        publishedPostRequest4.setAuthorId(staffUser.getId());
        publishedPostRequest4.setStatus(PostStatus.DRAFT);
        postService.createPost(publishedPostRequest4);

        log.info("Initialized Posts.");
    }
}