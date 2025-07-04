package com.swp08.dpss.dto.requests.survey;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateSurveyRequest {
    @NotBlank
    private String name;

    private String description;
    
}