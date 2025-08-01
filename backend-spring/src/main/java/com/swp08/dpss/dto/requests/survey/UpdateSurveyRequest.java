package com.swp08.dpss.dto.requests.survey;

import com.swp08.dpss.enums.SurveyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSurveyRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private String type;

    @NotNull
    private SurveyStatus status;
}
