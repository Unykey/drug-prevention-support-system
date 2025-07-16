package com.swp08.dpss.entity.course;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CourseSurveyId implements Serializable {
    private Long courseId;
    private Long surveyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseSurveyId that)) return false;
        return courseId.equals(that.courseId) && surveyId.equals(that.surveyId);
    }
    @Override
    public int hashCode() {
        return 31 * courseId.hashCode() + surveyId.hashCode();
    }
}
