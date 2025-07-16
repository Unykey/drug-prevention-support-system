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
//@ToString
//@RequiredArgsConstructor
//@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey_id;

    @Column (name = "Question")
    private String question;

    @Column (name = "Type", nullable = false)
//    private String type;
    private QuestionTypes type;

    @Column (name = "Value", nullable = false, columnDefinition = "varchar(50)")
    private String value;

    @Column (name = "Solution")
    private String solution; //	Correct answer (only used in quiz/test for courses)

//    @ManyToOne
//    @JoinColumn(name = "Survey")
//    private Survey survey;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question_id")
    private List<SurveyAnswer> answers = new ArrayList<>();

//    public void addAnswer(SurveyAnswer answer) {
//        answers.add(answer);
//        answer.setQuestion(this);
//    }
//
//    public void removeAnswer(SurveyAnswer answer) {
//        answers.remove(answer);
//        answer.setQuestion(null);
//    }
//
//    public SurveyQuestion() {
//    }
//
//    public SurveyQuestion(String question, String type, String solution) {
//        this.question = question;
//        this.type = type;
//        this.solution = solution;
//    }

}
