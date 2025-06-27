package com.swp08.dpss.repository;

import com.swp08.dpss.entity.SurveyAnswer;
import com.swp08.dpss.enums.SurveyAnswerStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswer, Long> {
    List<SurveyAnswer> findByQuestionIdAndStatus(Long questionId, SurveyAnswerStatus status);

    List<SurveyAnswer> findBySurveyIdAndStatus(Long surveyId, SurveyAnswerStatus status);
}
