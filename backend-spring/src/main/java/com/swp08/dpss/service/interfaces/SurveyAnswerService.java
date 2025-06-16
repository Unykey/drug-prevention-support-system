package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.SurveyAnswerDto;
import com.swp08.dpss.entity.SurveyQuestion;

import java.util.List;

public interface SurveyAnswerService {
    List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId);
    void deleteSurveyAnswer(Long surveyAnswerId);
    SurveyAnswerDto submitAnswer(SubmitSurveyAnswerRequest request);
}
