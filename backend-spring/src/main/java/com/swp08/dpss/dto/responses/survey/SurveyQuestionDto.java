package com.swp08.dpss.dto.responses.survey;

import com.swp08.dpss.enums.QuestionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SurveyQuestionDto {
    private Long id;
    private String question;
    private QuestionTypes type;
    private String solution;
    private List<String> value;
    private Long surveyId;
}
