package com.swp08.dpss.dto.responses.survey;

import com.swp08.dpss.enums.SurveyStatus;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailsDto {
    private Long id;
    private String name;
    private String description;
    private List<SurveyQuestionDto> questions;
    private String surveyType;
    private SurveyStatus surveyStatus;
}