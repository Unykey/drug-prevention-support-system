package com.swp08.dpss.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateSurveyQuestionRequest {

    @NotBlank
    private String question;

    @NotBlank
    private String type;  // e.g., "MULTIPLE_CHOICE", "SHORT_ANSWER"

    @NotBlank
    private String solution;
}