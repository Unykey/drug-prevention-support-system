package com.swp08.dpss.entity.survey;

import com.swp08.dpss.entity.client.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class SurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long answer_id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SurveyQuestion question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private QuestionOption option; // Contain selected answer

    @Column (name = "Content", nullable = false)
    private String content; // Contain free-text answer

    @Column (name = "ResultScore")
    private Integer resultScore;

    @Column (name = "SubmittedAt", nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();
}

