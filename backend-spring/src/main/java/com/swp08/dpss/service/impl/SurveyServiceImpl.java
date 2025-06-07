package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.SurveyDto;
import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyService surveyService;

    public SurveyServiceImpl(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public Survey createSurvey(SurveyDto dto) {
        Survey survey = new Survey();
        survey.setName(dto.getName());
        survey.setDescription(dto.getDescription());
        return surveyRepository.save(survey);
    }

    @Override
    public List<Survey> getAllSurveys() {
        return List.of();
    }

    @Override
    public Survey getSurveyById(long id) {
        return null;
    }


}
