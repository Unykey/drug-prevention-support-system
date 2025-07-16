package com.swp08.dpss.entity.program;

import com.swp08.dpss.entity.client.Guardian;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.course.CourseSurvey;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.SurveyTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Title", nullable = false, columnDefinition = "varchar(50)")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Start_Date")
    private LocalDateTime start_date;

    @Column(name = "End_Date")
    private LocalDateTime end_date;

    @Column(name = "Location")
    private String location;

    @Column(nullable = false)
    private LocalDate created_at;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "created_by")
    private User user;

    @OneToMany(mappedBy = "program",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProgramParticipation> programParticipations = new ArrayList<>();

    @OneToMany(
            mappedBy = "program",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProgramSurvey> programSurveyList = new ArrayList<>();
}
