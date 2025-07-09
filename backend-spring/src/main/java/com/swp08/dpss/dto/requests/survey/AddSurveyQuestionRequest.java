package com.swp08.dpss.dto.requests.survey;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSurveyQuestionRequest {
    @NotBlank
    private String question;

    @NotBlank
    private String type;

    private String solution;

}
