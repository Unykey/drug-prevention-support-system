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
    private Survey survey_id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SurveyQuestion question_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column (name = "Content", nullable = false)
    private String content;

    @Column (name = "ResultScore")
    private Integer resultScore;

    @Column (name = "SubmittedAt", nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

}

