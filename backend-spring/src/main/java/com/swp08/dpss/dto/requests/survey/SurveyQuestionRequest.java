package com.swp08.dpss.dto.requests.survey;

import com.swp08.dpss.enums.QuestionTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionRequest {
    @NotBlank
    private String question;

    @NotBlank
    private QuestionTypes type;

    private String solution;

}
