package com.swp08.dpss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class SurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "varchar(10)")
    private long Id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answerValue;

    @ManyToOne
    @JoinColumn(name = "ResponseId")
    private SurveyResponse response;

    @ManyToOne
    @JoinColumn(name = "QuestionId")
    private SurveyQuestion question;

    public SurveyAnswer() {
    }

    public SurveyAnswer(Integer answerId, String answerValue) {
        this.answerId = answerId;
        this.answerValue = answerValue;
    }
}
