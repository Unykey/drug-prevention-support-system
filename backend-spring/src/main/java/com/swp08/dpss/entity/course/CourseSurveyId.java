package com.swp08.dpss.entity.course;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

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
        return Objects.equals(courseId, that.courseId) && Objects.equals(surveyId, that.surveyId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(courseId, surveyId);
    }
}
