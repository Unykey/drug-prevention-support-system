package com.swp08.dpss.repository.survey;

import com.swp08.dpss.entity.survey.AnswerOption;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerOptionRepository
        extends JpaRepository<AnswerOption, Long> {
    Optional<AnswerOption> findByQuestionAndContent(SurveyQuestion question, String content);
}
