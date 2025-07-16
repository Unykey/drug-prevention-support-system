package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.requests.survey.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SurveyQuestionService {
    List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId);
    void softDeleteSurveyQuestionById(Long surveyQuestionId);

    void hardDeleteSurveyQuestionById(Long surveyQuestionId);

    SurveyQuestionDto addQuestionToSurvey(Long id, SurveyQuestionRequest questionDto);

    SurveyQuestionDto getQuestionById(Long id);

    @Transactional
    SurveyQuestionDto updateQuestion(Long id, SurveyQuestionRequest request);
}
