package com.swp08.dpss.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyAnswerDto {
    private Long id;
    private String content;
    private int resultScore;
    private LocalDateTime submittedAt;

    private String questionText;

    private String userName;
}
