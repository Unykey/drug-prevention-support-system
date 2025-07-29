package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.consultant.Availability;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Course")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private CourseStatus status = CourseStatus.DRAFT; // DRAFT, PUBLISHED, ARCHIVED

    @Column (name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToMany
    @JoinTable(
            name = "course_target_group",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "target_group_id")
    )
    private Set<TargetGroup> targetGroups = new HashSet<>();

    @Column
    private boolean isPublished; // default is false

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<CourseLesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY)
    private Set<CourseEnrollment> enrollments = new HashSet<>();

    @OneToMany(
            mappedBy = "course",
            fetch = FetchType.LAZY)
    private Set<CourseSurvey> courseSurveyList = new HashSet<>();

    public void addEnrollment(CourseEnrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setCourse(this);
    }
    public void removeEnrollment(CourseEnrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setCourse(null);
    }
    public void addLesson(CourseLesson lesson) {
        lessons.add(lesson);
        lesson.setCourse(this);
    }
    public void removeLesson(CourseLesson lesson) {
        lessons.remove(lesson);
        lesson.setCourse(null);
    }
    public void addCourseSurvey(CourseSurvey courseSurvey) {
        courseSurveyList.add(courseSurvey);
        courseSurvey.setCourse(this);
    }
    public void removeCourseSurvey(CourseSurvey courseSurvey) {
        courseSurveyList.remove(courseSurvey);
        courseSurvey.setCourse(null);
    }
}

