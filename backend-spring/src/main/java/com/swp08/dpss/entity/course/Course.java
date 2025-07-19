package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    private List<String> targetGroups; // ["student", "teacher", "parent"]

    @Column
    private boolean isPublished; // default is false

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CourseLesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY)
    private List<CourseEnrollment> enrollments = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            fetch = FetchType.LAZY)
    private List<CourseSurvey> courseSurveyList = new ArrayList<>();

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

