package com.swp08.dpss.repository.survey;

import com.swp08.dpss.entity.survey.QuestionOption;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    Optional<QuestionOption> findByQuestionAndContent(SurveyQuestion question, String content);
}
