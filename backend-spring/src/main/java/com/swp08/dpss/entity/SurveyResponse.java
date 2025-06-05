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
    @Column(name = "Id")
    private long id;

    @Column (name = "SubmittedAt")
    private Date submittedAt;

    @Column (name = "ResultScore")
    private int resultScore;

    @Column (name = "Answer")
    private String answer;

    @ManyToOne
    @JoinColumn (name = "SurveyId")
    private Survey survey;

    @ManyToOne
    @JoinColumn (name = "QuestionId")
    private SurveyQuestion question;

    public SurveyResponse() {
    }

    public SurveyResponse(long id, Date submittedAt, int resultScore) {
        this.submittedAt = submittedAt;
        this.resultScore = resultScore;
    }
}

