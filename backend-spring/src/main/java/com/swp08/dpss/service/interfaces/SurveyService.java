package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.CreateSurveyRequest;
import com.swp08.dpss.dto.responses.SurveyDetailsDto;
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
}
