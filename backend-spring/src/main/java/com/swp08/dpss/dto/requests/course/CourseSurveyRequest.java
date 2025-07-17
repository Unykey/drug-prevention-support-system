package com.swp08.dpss.dto.requests.course;

import com.swp08.dpss.enums.CourseSurveyRoles;
import com.swp08.dpss.enums.SurveyTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSurveyRequest {
    private Long surveyId;
    private CourseSurveyRoles role;
    private SurveyTypes surveyType;
}
