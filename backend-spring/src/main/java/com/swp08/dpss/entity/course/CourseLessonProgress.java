package com.swp08.dpss.entity.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name = "CourseLessonProgress")
@Getter
@Setter
@NoArgsConstructor
public class CourseLessonProgress {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "UserId", referencedColumnName = "UserId"),
            @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    })
    private CourseEnrollment enrollment;

    @OneToOne
    @JoinColumn (name = "Lesson")
    private CourseLesson lesson;

    @Column (name = "IsCompleted")
    private boolean completed;

    @Column (name = "CompleteDate")
    private LocalDateTime completedAt;
}
