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
    private Integer resultScore;
    private LocalDateTime submittedAt;

    private Long surveyId;
    private Long questionId;
    private Long userId;
}
