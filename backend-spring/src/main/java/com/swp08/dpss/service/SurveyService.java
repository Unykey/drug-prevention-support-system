package com.swp08.dpss.service;

import com.swp08.dpss.dto.SurveyDto;
import com.swp08.dpss.entity.Survey;

import java.util.List;

public interface SurveyService{
    Survey createSurvey(SurveyDto dto);
    List<Survey> getAllSurveys();
    Survey getSurveyById(long id);
}
