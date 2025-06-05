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
    @Column (name = "Id")
    private long id;

    @Column (name = "Question", nullable = false)
    private String question;

    @Column (name = "Type", nullable = false, columnDefinition = "varchar(20)")
    private String type;

    @Column (name = "Answer", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "SurveyId")
    private Survey survey;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
    private List<SurveyResponse> responses = new ArrayList<>();

    public void addResponse(SurveyResponse response) {
        responses.add(response);
        response.setQuestion(this);
    }

    public void removeResponse(SurveyResponse response) {
        responses.remove(response);
        response.setQuestion(null);
    }

    public SurveyQuestion() {
    }

    public SurveyQuestion(long id, String question, String type) {
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
