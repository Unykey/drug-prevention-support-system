package com.swp08.dpss.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp08.dpss.entity.course.CourseEnrollment;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.enums.User_Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String password; // In a real app, this MUST be hashed

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<SurveyAnswer> answers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<CourseEnrollment> courseEnrollments = new ArrayList<>();

    @JsonBackReference // This side will NOT be serialized when serializing a Parent that contains this User
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private User_Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_guardian", // name of join table between user and Guardian
            joinColumns = @JoinColumn(name = "user_id"), // FK column in join table referencing User
            inverseJoinColumns = @JoinColumn(name = "guardian_id") // FK column in join table referencing Guardian
    )
    private List<Guardian> guardians = new ArrayList<>();

    // Methods for managing the relationship
    public void addGuardian(Guardian guardian){
        this.guardians.add(guardian);
        guardian.getUser().add(this); // Maintain bidirectional consistency
    }

    public void removeGuardian(Guardian guardian){
        this.guardians.remove(guardian);
        guardian.getUser().remove(this); // Maintain bidirectional consistency
    }

    public void removeAnswer(SurveyAnswer answer) {
        answers.remove(answer);
        answer.setUser(null);
    }

    public void addAnswer(SurveyAnswer answer) {
        answers.add(answer);
        answer.setUser(this);
    }

    public void addCourseEnrollment(CourseEnrollment courseEnrollment) {
        courseEnrollments.add(courseEnrollment);
        courseEnrollment.setUser(this);
    }

    public void removeCourseEnrollment(CourseEnrollment courseEnrollment) {
        courseEnrollments.remove(courseEnrollment);
        courseEnrollment.setUser(null);
    }
}
