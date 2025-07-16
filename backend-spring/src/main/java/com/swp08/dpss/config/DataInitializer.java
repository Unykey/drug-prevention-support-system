package com.swp08.dpss.config;

import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.GuardianCreationRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.enums.*;
import com.swp08.dpss.service.interfaces.UserService;

import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import com.swp08.dpss.service.interfaces.survey.SurveyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Slf4j //Log stuffs :D
@Component
@AllArgsConstructor
@Profile("local")
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final SurveyService surveyService;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyAnswerService surveyAnswerService;

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
            log.info("Survey initialization failed:" + e.getMessage());
        }

        try {
            answerInit();
            log.info("Survey Answer initialization complete.");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Survey Answer initialization failed: " + e.getMessage());
        }

        log.info("DataInitializer finished.");
    }

    private void surveyInit() {
        SurveyDetailsDto assist = surveyService.createSurvey(
                new CreateSurveyRequest(
                        "ASSIST",
                        SurveyTypes.QUIZ,
                        SurveyStatus.PUBLISHED,
                        "Đánh giá mức độ sử dụng chất gây nghiện"
                )
        );

        SurveyDetailsDto crafft = surveyService.createSurvey(
                new CreateSurveyRequest(
                        "CRAFFT",
                        SurveyTypes.QUIZ,
                        SurveyStatus.PUBLISHED,
                        "Sàng lọc nguy cơ sử dụng chất gây nghiện"
                )
        );

        SurveyDetailsDto dast10 = surveyService.createSurvey(
                new CreateSurveyRequest(
                        "DAST-10",
                        SurveyTypes.QUIZ,
                        SurveyStatus.PUBLISHED,
                        "Khảo sát mức độ nghiện các loại ma túy"
                )
        );

        surveyQuestionService.addQuestionToSurvey(assist.getId(), new SurveyQuestionRequest("Bạn đã từng sử dụng rượu chưa?", QuestionTypes.YN, "Có"));
        surveyQuestionService.addQuestionToSurvey(assist.getId(), new SurveyQuestionRequest("Bạn có hút thuốc lá không?", QuestionTypes.YN, "Không"));

        surveyQuestionService.addQuestionToSurvey(crafft.getId(), new SurveyQuestionRequest("Bạn từng đi xe có người dùng chất?", QuestionTypes.YN, "Có"));
        surveyQuestionService.addQuestionToSurvey(crafft.getId(), new SurveyQuestionRequest("Bạn có muốn giảm sử dụng chất đó không?", QuestionTypes.YN, "Có"));

        surveyQuestionService.addQuestionToSurvey(dast10.getId(), new SurveyQuestionRequest("Bạn dùng heroin 30 ngày qua?", QuestionTypes.YN, "Không"));
        surveyQuestionService.addQuestionToSurvey(dast10.getId(), new SurveyQuestionRequest("Bạn thấy khó chịu nếu không dùng chất?", QuestionTypes.YN, "Có"));
    }


    private void answerInit() {
        // Create survey
        SurveyDetailsDto survey = surveyService.createSurvey(new CreateSurveyRequest("DEMO_SURVEY",SurveyTypes.QUIZ,SurveyStatus.PUBLISHED, "Demo survey for testing answers"));
        Long surveyId = survey.getId();

        // Create questions and get IDs
        SurveyQuestionDto q1 = surveyQuestionService.addQuestionToSurvey(surveyId,
                new SurveyQuestionRequest("Bạn có uống rượu không?", QuestionTypes.YN, "Có"));
        SurveyQuestionDto q2 = surveyQuestionService.addQuestionToSurvey(surveyId,
                new SurveyQuestionRequest("Bạn có hút thuốc không?", QuestionTypes.YN, "Không"));

        Long q1Id = q1.getId();
        Long q2Id = q2.getId();

        // Submit answers from user 1
        SubmitSurveyAnswerRequest answer1 = new SubmitSurveyAnswerRequest();
        answer1.setUserId(1L);
        answer1.setContent("Có");

        SubmitSurveyAnswerRequest answer2 = new SubmitSurveyAnswerRequest();
        answer2.setUserId(1L);
        answer2.setContent("Không");

        surveyAnswerService.submitAnswer(surveyId, q1Id, answer1);
        surveyAnswerService.submitAnswer(surveyId, q2Id, answer2);
    }


}
