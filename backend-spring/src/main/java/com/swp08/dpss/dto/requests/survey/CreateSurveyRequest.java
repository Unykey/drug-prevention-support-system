package com.swp08.dpss.dto.requests.survey;

import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.ProgramSurveyRoles;
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
    private ProgramSurveyRoles surveyType;
    private SurveyStatus surveyStatus;
    private String description;

}