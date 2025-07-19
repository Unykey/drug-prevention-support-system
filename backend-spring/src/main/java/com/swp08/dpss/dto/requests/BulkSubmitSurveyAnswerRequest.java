package com.swp08.dpss.dto.requests.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@Data
public class BulkSubmitSurveyAnswerRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long surveyId;

    @NotNull
    private List<AnswerSubmission> answers;

    @AllArgsConstructor
    @Data
    public static class AnswerSubmission {
        @NotNull
        private Long questionId;

        private String content;  // the user's actual answer
    }
}