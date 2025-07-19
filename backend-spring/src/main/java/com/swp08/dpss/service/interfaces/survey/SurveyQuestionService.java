package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;

import java.util.List;

public interface SurveyQuestionService {
    SurveyQuestionDto addQuestionToSurvey(Long surveyId, SurveyQuestionRequest questionRequest);
    SurveyQuestionDto getQuestionById(Long questionId);
    List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId);
    SurveyQuestionDto updateQuestion(Long questionId, SurveyQuestionRequest questionRequest);
    void deleteQuestion(Long questionId);
}