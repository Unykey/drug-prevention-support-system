package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.SurveyAnswerDto;

import java.util.List;

public interface SurveyAnswerService {
    List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId);
    void softDeleteSurveyAnswer(Long surveyAnswerId);
    SurveyAnswerDto submitAnswer(Long surveyId, Long questionId, SubmitSurveyAnswerRequest request);

    void submitAllAnswers(BulkSubmitSurveyAnswerRequest request);

    void hardDeleteSurveyAnswer(Long answerId);
}
