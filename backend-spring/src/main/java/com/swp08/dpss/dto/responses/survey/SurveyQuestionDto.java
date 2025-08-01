package com.swp08.dpss.dto.responses.survey;

import com.swp08.dpss.enums.QuestionTypes;
import lombok.Data;

import java.util.List;

@Data
public class SurveyQuestionDto {
    private Long id;
    private String question;
    private QuestionTypes type;
    // `solution` and `value` fields are removed
    private List<AnswerOptionDto> options; // New field for options
    private Long surveyId;
}