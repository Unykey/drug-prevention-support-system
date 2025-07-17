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
    private Survey survey_id;

    @Column (name = "Question")
    private String question;

    @Column (name = "Type", nullable = false)
    private QuestionTypes type;

    @Column (name = "Value", columnDefinition = "varchar(50)")
    private String value;

    @Column (name = "Solution")
    private String solution; //	Correct answer (only used in quiz/test for courses)

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question_id")
    private List<SurveyAnswer> answers = new ArrayList<>();

    public void addAnswer(SurveyAnswer surveyAnswer) {
        answers.add(surveyAnswer);
        surveyAnswer.setQuestion_id(this);
    }
    public void removeAnswer(SurveyAnswer surveyAnswer) {
        answers.remove(surveyAnswer);
        surveyAnswer.setQuestion_id(null);
    }
}
