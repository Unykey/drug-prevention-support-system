package com.swp08.dpss.dto.requests.survey;

import lombok.Data;

@Data
public class SubmitSurveyAnswerRequest {
    private Long userId;
    private String content;
}
