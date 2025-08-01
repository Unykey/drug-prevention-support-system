package com.swp08.dpss.entity.program;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProgramSurveyId implements Serializable {
    private Long programId;
    private Long surveyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramSurveyId that)) return false;
        return Objects.equals(programId, that.programId) && Objects.equals(surveyId, that.surveyId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(programId, surveyId);
    }
}
