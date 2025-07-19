package com.swp08.dpss.dto.responses.survey;

import lombok.Data;

@Data
public class QuestionOptionDto {
    private Long id;
    private String content;
    private boolean correct;
    // No need for questionId here in the DTO for response,
    // as it's typically nested under SurveyQuestionDto
}