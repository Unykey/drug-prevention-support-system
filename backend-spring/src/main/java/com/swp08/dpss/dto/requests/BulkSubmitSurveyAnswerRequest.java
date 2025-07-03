package com.swp08.dpss.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class BulkSubmitSurveyAnswerRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long surveyId;

    @NotNull
    private List<AnswerSubmission> answers;

    @Data
    public static class AnswerSubmission {
        @NotNull
        private Long questionId;

        private String content;  // the user's actual answer
    }
}