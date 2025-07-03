package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.survey.Survey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CourseLesson")
@Getter
@Setter
@NoArgsConstructor
public class CourseLesson {
    @Id
    @GeneratedValue  (strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "Title", nullable = false)
    private String title;

    @Column (name = "Type", nullable = false)
    private String type; // READING, VIDEO, QUIZ

    @Lob
    @Column (name = "ContentLink")
    private String content; // for reading, text, or video URL

    @ManyToOne
    @JoinColumn (name = "CourseId")
    private Course course;

    @Column (name = "Order", nullable = false)
    private int orderIndex;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "courseLesson")
    @Column (name = "SurveyId")
    private Survey survey; // optional

    public CourseLesson(String title, String type, String content, Course course, int orderIndex) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.course = course;
        this.orderIndex = orderIndex;
    }
}

