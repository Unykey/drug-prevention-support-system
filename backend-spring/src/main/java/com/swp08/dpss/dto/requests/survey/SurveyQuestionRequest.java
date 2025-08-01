package com.swp08.dpss.dto.requests.survey;

import com.swp08.dpss.enums.QuestionTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionRequest {
    @NotBlank(message = "Question content cannot be blank")
    private String question;

    @NotNull(message = "Question type must be specified")
    private QuestionTypes type;

    private List<AnswerOptionRequest> options;
}