package com.swp08.dpss.dto.requests.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSurveyRequest {
    @NotBlank
    private String name;

    private String description;

}