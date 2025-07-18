package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.program.Program;
import com.swp08.dpss.entity.program.ProgramSurveyId;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseSurveyRoles;
import com.swp08.dpss.enums.ProgramSurveyRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseSurvey {
    @EmbeddedId
    private CourseSurveyId courseSurveyId;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("surveyId")
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(name = "Type", nullable = false)
    private CourseSurveyRoles role;

    public void setCourse(Course course) {
        this.course = course;
        if (this.course !=  null && !this.course.getCourseSurveyList().contains(this)) {
            this.course.getCourseSurveyList().add(this);
        }
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
        if (this.survey !=  null && !this.survey.getCourseSurveyList().contains(this)) {
            this.survey.getCourseSurveyList().add(this);
        }
    }
}
