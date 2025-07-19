package com.swp08.dpss.dto.requests.survey;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class QuestionOptionRequest {
    // If updating, you might include an 'id'
    // private Long id;

    @NotBlank(message = "Option content cannot be blank")
    private String content;

    @NotNull(message = "isCorrect status cannot be null")
    private boolean correct; // Indicates if this option is the correct answer

    // Constructor for easier initialization in DataInitializer
    public QuestionOptionRequest(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }
}