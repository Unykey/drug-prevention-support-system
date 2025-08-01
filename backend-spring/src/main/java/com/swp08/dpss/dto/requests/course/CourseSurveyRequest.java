package com.swp08.dpss.dto.requests.course;

import com.swp08.dpss.enums.CourseSurveyRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSurveyRequest {
    private Long surveyId;
    private CourseSurveyRoles role;
    private String surveyType;
}
