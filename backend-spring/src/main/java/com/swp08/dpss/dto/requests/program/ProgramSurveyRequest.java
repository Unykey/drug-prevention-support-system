package com.swp08.dpss.dto.requests.program;

import com.swp08.dpss.enums.ProgramSurveyRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramSurveyRequest {
    private Long programId;
    private ProgramSurveyRoles role;
    private ProgramSurveyRoles surveyType;
}
