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
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"), // FK column in CourseLessonProgress, referencing user_id in CourseEnrollment
            @JoinColumn(name = "course_id", referencedColumnName = "course_id")// FK column in CourseLessonProgress, referencing course_id in CourseEnrollment
    })
    private CourseEnrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private CourseLesson lesson;

    @Column (name = "IsCompleted")
    private boolean completed;

    @Column (name = "CompleteDate")
    private LocalDateTime completedAt;
}
