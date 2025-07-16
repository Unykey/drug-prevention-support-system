package com.swp08.dpss.entity.course;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Type", nullable = false)
    private String type; // READING, VIDEO, QUIZ

    @Lob
    @Column(name = "ContentLink")
    private String content; // for reading, text, or video URL

    @OneToOne(mappedBy = "lesson",
            cascade = CascadeType.ALL
    )
    private CourseLessonProgress courseLessonProgress;

    @ManyToOne
    @JoinColumn(name = "CourseId")
    private Course course;

    @Column(name = "OrderIndex", nullable = false)
    private int orderIndex;
}

