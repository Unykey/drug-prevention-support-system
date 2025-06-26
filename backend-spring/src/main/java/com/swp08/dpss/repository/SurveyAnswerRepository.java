package com.swp08.dpss.repository;

import com.swp08.dpss.entity.SurveyAnswer;
import com.swp08.dpss.enums.SurveyAnswerStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswer, Long> {
    // Get all responses for a survey by a specific user
    List<SurveyAnswer> findBySurveyIdAndUserId(Long surveyId, Long userId);

    // Get responses submitted within a date range
    List<SurveyAnswer> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

    // Get all responses for a given question and answer value (e.g., "4")
    List<SurveyAnswer> findByQuestionIdAndContent(Long questionId, String answer);

    List<SurveyAnswer> findBySurveyIdAndUserIdAndStatus(Long surveyId, Long userId, SurveyAnswerStatus status);

    List<SurveyAnswer> findByQuestionIdAndContentAndStatus(Long questionId, String content, SurveyAnswerStatus status);
}
