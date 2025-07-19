package com.swp08.dpss.repository.survey;

import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    List<SurveyAnswer> findAllBySurvey_id(Long surveyId);

    Optional<SurveyAnswer> findByQuestionAndUser(SurveyQuestion question, User user);

    List<SurveyAnswer> findAllByUser(User user);
}
