package com.swp08.dpss.dto.requests.survey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitSurveyAnswerRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotBlank(message = "Answer content cannot be blank")
    private String content;
}
