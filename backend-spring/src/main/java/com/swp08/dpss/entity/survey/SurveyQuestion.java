package com.swp08.dpss.entity.survey;

import com.swp08.dpss.enums.SurveyQuestionStatus;
import jakarta.persistence.*;
import lombok.Getter;
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
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private Long id;

    @Column (name = "Question")
    private String question;

    @Column (name = "Type", nullable = false, columnDefinition = "varchar(20)")
    private String type;

    @Column (name = "Solution")
    private String solution;

    @Enumerated(EnumType.STRING)
    @Column (name = "Status")
    private SurveyQuestionStatus status = SurveyQuestionStatus.VISIBLE;

    @ManyToOne
    @JoinColumn(name = "Survey")
    private Survey survey;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
    private List<SurveyAnswer> answers = new ArrayList<>();

    public void addAnswer(SurveyAnswer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(SurveyAnswer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    public SurveyQuestion() {
    }

    public SurveyQuestion(String question, String type, String solution) {
        this.question = question;
        this.type = type;
        this.solution = solution;
    }

}
