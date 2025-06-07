package com.swp08.dpss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyResponseDto {
    private Long userId;       // FK: User submitting the survey
    private Long surveyId;     // FK: Survey being answered
    private Long questionId;   // FK: Question being answered
    private String answer;     // Actual response
    private int resultScore;   // Optional or computed
    private LocalDateTime submittedAt;
}
