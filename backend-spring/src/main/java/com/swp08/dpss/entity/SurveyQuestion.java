package com.swp08.dpss.entity;

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
    @Column (name = "Id", columnDefinition = "varchar(10)")
    private long id;

    @Column (name = "Question", nullable = false)
    private String question;

    @Column (name = "Type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "SurveyId")
    private Survey survey;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
    private List<SurveyAnswer> answers = new ArrayList<SurveyAnswer>();

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

    public SurveyQuestion(long id, String question, String type) {
        this.id = id;
        this.question = question;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SurveyQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
