package com.swp08.dpss.dto.responses.survey;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SurveyAnswerDto {
    private Long id;
    private Long questionId;
    private String questionText; // Keep this if useful for display
    private Long userId;
    private Long surveyId;
    private Long optionId; // New field for selected option ID
    private String content; // For free-text answers
    private Integer resultScore;
    private LocalDateTime submittedAt;
}