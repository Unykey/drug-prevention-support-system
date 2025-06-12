package com.swp08.dpss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class SurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column (name = "SubmittedAt")
    private LocalDateTime submittedAt;

    @Column (name = "ResultScore")
    private int resultScore;

    @Column (name = "content")
    private String content;

    @ManyToOne
    @JoinColumn (name = "Survey")
    private Survey survey;

    @ManyToOne
    @JoinColumn (name = "Question")
    private SurveyQuestion question;

    public SurveyAnswer() {
    }

    public SurveyAnswer(String answer) {
        this.answer = answer;
    }
}

