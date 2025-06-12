package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.CreateSurveyRequest;
import com.swp08.dpss.dto.responses.SurveyDetailsDto;

import java.util.List;

public interface SurveyService {
    SurveyDetailsDto createSurvey(CreateSurveyRequest request);
    List<SurveyDetailsDto> getAllSurveys();
    SurveyDetailsDto getSurveyById(long id);
    List<SurveyDetailsDto> searchSurveysByName(String name);
}
