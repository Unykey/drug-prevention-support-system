package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.survey.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.requests.survey.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;

import java.util.List;

public interface SurveyQuestionService {
    List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId);
    void softDeleteSurveyQuestionById(Long surveyQuestionId);

    void hardDeleteSurveyQuestionById(Long surveyQuestionId);

    SurveyQuestionDto addQuestionToSurvey(Long id, AddSurveyQuestionRequest questionDto);

    SurveyQuestionDto getQuestionById(Long id);

    SurveyQuestionDto updateQuestion(Long id, UpdateSurveyQuestionRequest request);
}
