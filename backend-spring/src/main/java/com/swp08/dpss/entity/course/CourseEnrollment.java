package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "CourseEnrollment")
@Getter
@Setter
@NoArgsConstructor
public class CourseEnrollment {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "UserId")
    private User user;

    @ManyToOne
    @JoinColumn (name = "CourseId")
    private Course course;

    @Column (name = "EnrollDate")
    private LocalDateTime enrolledAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<LessonProgress> progress = new ArrayList<>();

    public void addProgress(LessonProgress progress) {
        this.progress.add(progress);
        progress.setEnrollment(this);
    }
    public void removeProgress(LessonProgress progress) {
        this.progress.remove(progress);
        progress.setEnrollment(null);
    }
}