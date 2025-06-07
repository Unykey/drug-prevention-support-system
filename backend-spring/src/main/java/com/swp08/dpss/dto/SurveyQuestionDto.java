package com.swp08.dpss.dto;

import lombok.Data;

@Data
public class SurveyQuestionDto {
    private String question;
    private String type;       // e.g., "Multiple Choice", "Short Answer"
    private String answer;     // correct answer or default
    private Long surveyId;     // FK: reference to Survey
}
