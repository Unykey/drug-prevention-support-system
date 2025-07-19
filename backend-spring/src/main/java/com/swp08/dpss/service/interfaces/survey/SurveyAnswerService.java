package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.survey.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface SurveyAnswerService {
    // IMPORTANT: The `userEmail` parameter is crucial for security.
    SurveyAnswerDto submitAnswer(Long surveyId, Long questionId, SubmitSurveyAnswerRequest answerRequest, String userEmail);

    // You might have a bulk submission method
    // List<SurveyAnswerDto> submitAllAnswers(Long surveyId, Map<Long, SubmitSurveyAnswerRequest> answersByQuestionId, String userEmail);

    List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId);
    List<SurveyAnswerDto> getAnswersByUserId(Long userId);
    SurveyAnswerDto getAnswerById(Long answerId);
    void deleteAnswer(Long answerId);

    void submitAllAnswers(Long surveyId, @Valid BulkSubmitSurveyAnswerRequest request, String username);
}