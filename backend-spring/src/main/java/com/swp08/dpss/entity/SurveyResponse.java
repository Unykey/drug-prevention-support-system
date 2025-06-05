package com.swp08.dpss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class SurveyResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "varchar(10)")
    private long id;

    @Column (name = "SubmittedAt", nullable = false)
    private Date submittedAt;

    @Column (name = "ResultScore")
    private int resultScore;

    @ManyToOne
    @JoinColumn (name = "Survey")
    private Survey survey;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "response")
    private List<SurveyAnswer> answers = new ArrayList<SurveyAnswer>();

    public void addAnswer(SurveyAnswer answer) {
        answers.add(answer);
        answer.setResponse(this);
    }

    public void removeAnswer(SurveyAnswer answer) {
        answers.remove(answer);
        answer.setResponse(null);
    }

    public SurveyResponse() {
    }

    public SurveyResponse(long id, Date submittedAt, int resultScore) {
        this.submittedAt = submittedAt;
        this.resultScore = resultScore;
    }
}

