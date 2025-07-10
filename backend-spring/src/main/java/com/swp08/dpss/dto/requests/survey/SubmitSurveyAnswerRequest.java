package com.swp08.dpss.dto.requests.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitSurveyAnswerRequest {
    private Long userId;
    private String content;
}
