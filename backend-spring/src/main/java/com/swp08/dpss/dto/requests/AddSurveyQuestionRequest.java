package com.swp08.dpss.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddSurveyQuestionRequest {
    @NotBlank
    private String question;

    @NotBlank
    private String type;

    private String solution;
}
