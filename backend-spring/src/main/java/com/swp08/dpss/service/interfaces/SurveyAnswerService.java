package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.responses.SurveyAnswerDto;

import java.util.List;

public interface SurveyAnswerService {
    List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId);
}
