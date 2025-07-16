package com.swp08.dpss.entity.course;

import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Course")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "Title", nullable = false)
    private String title;

    @Column (name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column (name = "Status")
    private CourseStatus status = CourseStatus.DRAFT; // DRAFT, PUBLISHED, ARCHIVED

    @ElementCollection
    private List<String> targetGroups; // ["student", "teacher", "parent"]

    @Column (name = "StartDate", nullable = false)
    private LocalDate startDate = LocalDate.now();
    @Column (name = "EndDate")
    private LocalDate endDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    @Column (name = "Lessons")
    private List<CourseLesson> lessons = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    @Column (name = "Enrollments")
    private List<CourseEnrollment> enrollments = new ArrayList<>();

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
    private List<Survey> surveys = new ArrayList<>();

    public void addCourseLesson(CourseLesson lesson) {
        lessons.add(lesson);
        lesson.setCourse(this);
    }

    public void removeCourseLesson(CourseLesson lesson) {
        lessons.remove(lesson);
    }

    public void addEnrollment(CourseEnrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setCourse(this);
    }
    public void removeEnrollment(CourseEnrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setCourse(null);
    }

    public void addSurvey(Survey survey) {
        surveys.add(survey);
        survey.setCourse(this);
    }

    public void removeSurvey(Survey survey) {
        surveys.remove(survey);
        survey.setCourse(null);
    }

    public Course(String title, String description, CourseStatus status, List<String> targetGroups, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.targetGroups = targetGroups;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

