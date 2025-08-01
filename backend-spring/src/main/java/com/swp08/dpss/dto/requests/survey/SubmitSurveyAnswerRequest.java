package com.swp08.dpss.dto.requests.survey;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor; // Add NoArgsConstructor

@Data
@NoArgsConstructor // Add NoArgsConstructor
public class SubmitSurveyAnswerRequest {
    // userId should still NOT be in the request body for security (as discussed)
    // private Long userId; // This field should be removed from the DTO

    private Long optionId; // For multiple-choice/selection questions
    private String content; // For free-text input questions

    // Constructor for convenience (adjust based on new fields)
    public SubmitSurveyAnswerRequest(String content) {
        this.content = content;
    }
}