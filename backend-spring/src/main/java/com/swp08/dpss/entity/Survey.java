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
//@RequiredArgsConstructor
//@AllArgsConstructor //@NoArgsConstructor break and I have no idea why
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id", columnDefinition = "varchar(10)")
    private long id;

    @Column (name = "Name", nullable = false, columnDefinition = "nvarchar(50)")
    private String name;

    @Column (name = "Description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "survey")
    private List<SurveyQuestion> questions = new ArrayList<SurveyQuestion>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "survey")
    private List<SurveyResponse> responses = new ArrayList<SurveyResponse>();

    public void addQuestion(SurveyQuestion question) {
        questions.add(question);
        question.setSurvey(this);
    }

    public void addResponse(SurveyResponse response) {
        responses.add(response);
        response.setSurvey(this);
    }

    public void removeQuestion(SurveyQuestion question) {
        questions.remove(question);
        question.setSurvey(null);
    }

    public void removeResponse(SurveyResponse response) {
        responses.remove(response);
        response.setSurvey(null);
    }

    public Survey() {
    }

    public Survey(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
