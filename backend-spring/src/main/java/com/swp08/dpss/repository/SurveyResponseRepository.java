package com.swp08.dpss.repository;

import com.swp08.dpss.entity.SurveyResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SurveyResponseRepository extends CrudRepository<SurveyResponse, Long> {
    // Get all responses for a survey by a specific user
    List<SurveyResponse> findBySurveyIdAndUserId(Long surveyId, Long userId);

    // Get responses submitted within a date range
    List<SurveyResponse> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

    // Get all responses for a given question and answer value (e.g., "4")
    List<SurveyResponse> findByQuestionIdAndAnswer(Long questionId, String answer);
}
