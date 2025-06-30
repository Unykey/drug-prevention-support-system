package com.swp08.dpss.entity.survey;

import com.swp08.dpss.entity.User;
import com.swp08.dpss.enums.SurveyAnswerStatus;
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
    private Long id;

    @Column (name = "SubmittedAt")
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column (name = "ResultScore")
    private int resultScore;

    @Column (name = "Content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column (name = "Status",  nullable = false)
    private SurveyAnswerStatus status = SurveyAnswerStatus.VISIBLE;

    @ManyToOne
    @JoinColumn (name = "Survey")
    private Survey survey;

    @ManyToOne
    @JoinColumn (name = "Question")
    private SurveyQuestion question;

    @ManyToOne
    @JoinColumn (name = "\"User\"")
    private User user;

    public SurveyAnswer() {
    }

    public SurveyAnswer(String content, Survey survey, SurveyQuestion question, User user) {
        this.content = content;
        survey.addAnswer(this);
        question.addAnswer(this);
        this.user = user;
    }
}

