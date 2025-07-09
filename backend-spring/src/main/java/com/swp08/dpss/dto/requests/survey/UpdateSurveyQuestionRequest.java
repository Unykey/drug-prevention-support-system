package com.swp08.dpss.dto.requests.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSurveyQuestionRequest {

    @NotBlank
    private String question;

    @NotBlank
    private String type;  // e.g., "MULTIPLE_CHOICE", "SHORT_ANSWER"

    @NotBlank
    private String solution;
}