package com.swp08.dpss.entity.program;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
        return programId.equals(that.programId) && surveyId.equals(that.surveyId);
    }
    @Override
    public int hashCode() {
        return 31 * programId.hashCode() + surveyId.hashCode();
    }
}
