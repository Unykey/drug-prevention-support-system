package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
    // Get all questions by survey ID
    List<SurveyQuestion> findBySurvey(Survey survey);

    List<SurveyQuestion> findBySurveyId(Long surveyId);

    // Find all multiple-choice questions
    List<SurveyQuestion> findByType(String type);

    void deleteBySurvey(Survey survey);

    void updateSurveyQuestion(SurveyQuestion surveyQuestion);

    void updateSurveyQuestionById(SurveyQuestion surveyQuestion);
}
