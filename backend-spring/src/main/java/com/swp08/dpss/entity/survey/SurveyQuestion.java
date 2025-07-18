package com.swp08.dpss.entity.survey;

import com.swp08.dpss.enums.QuestionTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private Long question_id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column (name = "Question")
    private String question;

    @Column (name = "Type", nullable = false)
    private QuestionTypes type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options = new ArrayList<>();

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
    private List<SurveyAnswer> answers = new ArrayList<>();

    public void addAnswer(SurveyAnswer surveyAnswer) {
        answers.add(surveyAnswer);
        surveyAnswer.setQuestion(this);
    }
    public void removeAnswer(SurveyAnswer surveyAnswer) {
        answers.remove(surveyAnswer);
        surveyAnswer.setQuestion(null);
    }
}
