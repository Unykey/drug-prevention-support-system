package com.swp08.dpss.dto.responses.course;

import com.swp08.dpss.enums.CourseSurveyRoles;
import com.swp08.dpss.enums.SurveyTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSurveyResponse {
    private Long surveyId;
    private CourseSurveyRoles role;
    private SurveyTypes surveyType; // Assuming this is the type of the linked survey
    // You might add survey title here if needed, e.g., private String surveyTitle;
}
