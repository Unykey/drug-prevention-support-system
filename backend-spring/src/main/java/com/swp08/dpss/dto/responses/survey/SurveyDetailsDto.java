package com.swp08.dpss.dto.responses.survey;

import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.SurveyTypes;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailsDto {
    private Long id;
    private String name;
    private String description;
    private List<SurveyQuestionDto> questions;
    private SurveyTypes surveyType;
    private SurveyStatus surveyStatus;
}