package com.swp08.dpss.entity.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name = "LessonProgress")
@Getter
@Setter
@NoArgsConstructor
public class LessonProgress {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "Enrollment")
    private CourseEnrollment enrollment;

    @OneToOne
    @JoinColumn (name = "Lesson")
    private CourseLesson lesson;

    @Column (name = "IsCompleted")
    private boolean completed;

    @Column (name = "CompleteDate")
    private LocalDateTime completedAt;

    public LessonProgress(CourseEnrollment enrollment, CourseLesson lesson, boolean completed, LocalDateTime completedAt) {
        this.enrollment = enrollment;
        this.lesson = lesson;
        this.completed = completed;
        this.completedAt = completedAt;
    }
}
