package com.swp08.dpss.entity.survey;

import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.course.CourseSurvey;
import com.swp08.dpss.entity.program.ProgramSurvey;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.SurveyTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
//@RequiredArgsConstructor
//@AllArgsConstructor //@NoArgsConstructor break and I have no idea why
@NoArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private Long id;

    @Column (name = "Name", nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column (name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column (name = "Type", nullable = false)
    private SurveyTypes type;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status",  nullable = false)
    private SurveyStatus status = SurveyStatus.DRAFT; // default

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "created_by")
    private User user;

    @Column()
    private LocalDate created_at = LocalDate.now();

    @OneToMany(
            mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SurveyAnswer> answers = new ArrayList<>();

    @OneToMany(
            mappedBy = "survey",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SurveyQuestion> questions = new ArrayList<>();

    @OneToMany(
            mappedBy = "survey",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProgramSurvey> programSurveyList = new ArrayList<>();

    @OneToMany(
            mappedBy = "survey",
            fetch = FetchType.LAZY)
    private List<CourseSurvey> courseSurveyList = new ArrayList<>();

    public void addAnswer(SurveyAnswer answer) {
        answers.add(answer);
        answer.setSurvey(this);
    }

    public void removeAnswer(SurveyAnswer answer) {
        answers.remove(answer);
        answer.setSurvey(null);
    }

    public void addQuestion(SurveyQuestion question) {
        questions.add(question);
        question.setSurvey(this);
    }
    public void removeQuestion(SurveyQuestion question) {
        questions.remove(question);
        question.setSurvey(null);
    }
    public void addProgramSurvey(ProgramSurvey programSurvey) {
        programSurveyList.add(programSurvey);
        programSurvey.setSurvey(this);
    }
    public void removeProgramSurvey(ProgramSurvey programSurvey) {
        programSurveyList.remove(programSurvey);
        programSurvey.setSurvey(null);
    }
    public void addCourseSurvey(CourseSurvey courseSurvey) {
        courseSurveyList.add(courseSurvey);
        courseSurvey.setSurvey(this);
    }
    public void removeCourseSurvey(CourseSurvey courseSurvey) {
        courseSurveyList.remove(courseSurvey);
        courseSurvey.setSurvey(null);
    }
}
