package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.UpdateSurveyRequest;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.enums.SurveyStatus;


import java.util.List;

public interface SurveyService {
    SurveyDetailsDto createSurvey(CreateSurveyRequest request);
    List<SurveyDetailsDto> getAllSurveys();
    SurveyDetailsDto getSurveyById(Long id);
    List<SurveyDetailsDto> searchSurveysByName(String name);
    void softDeleteSurveyById(Long id);
    void hardDeleteSurveyById(Long id);

    List<SurveyDetailsDto> getSurveysByStatus(SurveyStatus surveyStatus);

    SurveyDetailsDto updateSurvey(Long id, UpdateSurveyRequest request);

    List<SurveyDetailsDto> getSurveysByTypeAndStatus(String type, SurveyStatus status);

    List<SurveyDetailsDto> getSurveysByType(String type);
}
