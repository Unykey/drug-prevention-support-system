package com.swp08.dpss.dto.responses.survey;

import lombok.Data;

import java.util.List;

@Data
public class SurveyQuestionDto {
    private Long id;
    private String question;
    private String type;
    private String solution;
    private List<String> value;
    private Long surveyId;
}
