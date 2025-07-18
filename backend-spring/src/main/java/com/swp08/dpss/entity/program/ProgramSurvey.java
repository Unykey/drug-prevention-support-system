package com.swp08.dpss.entity.program;

import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.ProgramSurveyRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProgramSurvey {
    @EmbeddedId
    private ProgramSurveyId programSurveyId;

    @ManyToOne
    @MapsId("programId")
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @MapsId("surveyId")
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(name = "Type", nullable = false)
    private ProgramSurveyRoles role;
}
