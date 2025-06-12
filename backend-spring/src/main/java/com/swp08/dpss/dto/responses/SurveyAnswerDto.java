package com.swp08.dpss.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyAnswerDto {
    private Long id;
    private String answer;
    private int resultScore;
    private LocalDateTime submittedAt;

    private Long questionId;
    private String questionText;

    private Long userId;
    private String userName;
}
