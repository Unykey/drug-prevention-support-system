package com.swp08.dpss.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
public class SubmitSurveyAnswerRequest {
    @NotNull
    private Long surveyId;

    @NotNull
    private Long questionId;

    @NotNull
    private Long userId;

    private String answer;
    private int resultScore;
    private LocalDateTime submittedAt;
}
