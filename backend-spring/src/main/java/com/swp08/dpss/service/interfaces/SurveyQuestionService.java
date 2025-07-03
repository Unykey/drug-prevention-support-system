package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.requests.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;

import java.util.List;

public interface SurveyQuestionService {
    List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId);
    void softDeleteSurveyQuestionById(Long surveyQuestionId);

    void hardDeleteSurveyQuestionById(Long surveyQuestionId);

    SurveyQuestionDto addQuestionToSurvey(Long id, AddSurveyQuestionRequest questionDto);

    SurveyQuestionDto getQuestionById(Long id);

    SurveyQuestionDto updateQuestion(Long id, UpdateSurveyQuestionRequest request);
}
