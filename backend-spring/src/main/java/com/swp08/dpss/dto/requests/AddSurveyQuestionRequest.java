package com.swp08.dpss.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddSurveyQuestionRequest {
    @NotBlank
    private String question;

    @NotBlank
    private String type;

    @NotNull
    private Long surveyId;
    private String solution;
}
