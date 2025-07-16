package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.client.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CourseEnrollment")
@Getter
@Setter
@NoArgsConstructor
public class CourseEnrollment {

    @EmbeddedId
    private CourseEnrollmentId id;

    @ManyToOne
    @MapsId("user") // FK user -> CourseEnrollmentId.user
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne
    @MapsId("course") // FK course -> CourseEnrollmentId.course
    @JoinColumn(name = "CourseId")
    private Course course;

    @Column(name = "EnrollDate")
    private LocalDateTime enrolledAt = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<CourseLessonProgress> progress = new ArrayList<>();

    // ✅ Constructor that sets composite key automatically
    public CourseEnrollment(User user, Course course) {
        this.user = user;
        this.course = course;
        this.id = new CourseEnrollmentId(user.getId(), course.getId());
    }

    public void addProgress(CourseLessonProgress progress) {
        this.progress.add(progress);
        progress.setEnrollment(this);
    }

    public void removeProgress(CourseLessonProgress progress) {
        this.progress.remove(progress);
        progress.setEnrollment(null);
    }
}
