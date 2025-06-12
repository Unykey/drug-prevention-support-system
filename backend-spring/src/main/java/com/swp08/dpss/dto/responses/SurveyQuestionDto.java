package com.swp08.dpss.dto.response;

import lombok.Data;

@Data
public class SurveyQuestionDto {
    private Long id;
    private String question;
    private String type;
    private String answer;
    private Long surveyId;
}
