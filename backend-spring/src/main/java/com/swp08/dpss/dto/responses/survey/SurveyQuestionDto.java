package com.swp08.dpss.dto.responses.survey;

import lombok.Data;

@Data
public class SurveyQuestionDto {
    private Long id;
    private String question;
    private String type;
    private String solution;
    private Long surveyId;
}
