package com.swp08.dpss.dto.responses.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAnswerDto {
    private Long id;
    private String content;
    private int resultScore;
    private LocalDateTime submittedAt;

    private String questionText;

    private Long surveyId;
    private Long questionId;
    private Long userId;
}
