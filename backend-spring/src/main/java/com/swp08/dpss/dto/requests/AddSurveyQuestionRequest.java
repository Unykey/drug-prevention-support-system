package com.swp08.dpss.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
