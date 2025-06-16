package com.swp08.dpss.entity;

import com.swp08.dpss.enums.SurveyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
//@RequiredArgsConstructor
//@AllArgsConstructor //@NoArgsConstructor break and I have no idea why
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private long id;

    @Column (name = "Name", nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column (name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private SurveyStatus status = SurveyStatus.DRAFT; // default

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "survey")
    private List<SurveyQuestion> questions = new ArrayList<SurveyQuestion>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "survey")
    private List<SurveyAnswer> answers = new ArrayList<SurveyAnswer>();

    public void addQuestion(SurveyQuestion question) {
        questions.add(question);
        question.setSurvey(this);
    }

    public void addResponse(SurveyAnswer response) {
        answers.add(response);
        response.setSurvey(this);
    }

    public void removeQuestion(SurveyQuestion question) {
        questions.remove(question);
        question.setSurvey(null);
    }

    public void removeResponse(SurveyAnswer answer) {
        answers.remove(answer);
        answer.setSurvey(null);
    }

    public Survey() {
    }

    public Survey(long id, String name, String description, SurveyStatus status) {
        this.name = name;
        this.description = description;
    }


}
