package com.swp08.dpss.entity.program;

import com.swp08.dpss.entity.consultant.Consultant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "Title", nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Start_Date")
    private LocalDateTime start_date;

    @Column(name = "End_Date")
    private LocalDateTime end_date;

    @Column(name = "Location")
    private String location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "program_consultants",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "consultant_id")
    )
    private Set<Consultant> hostedBy = new HashSet<>();

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

    public void addProgramParticipation(ProgramParticipation programParticipation) {
        programParticipations.add(programParticipation);
        programParticipation.setProgram(this);
    }
    public void removeProgramParticipation(ProgramParticipation programParticipation) {
        programParticipations.remove(programParticipation);
        programParticipation.setProgram(null);
    }
    public void addProgramSurvey(ProgramSurvey programSurvey) {
        programSurveyList.add(programSurvey);
        programSurvey.setProgram(this);
    }
    public void removeProgramSurvey(ProgramSurvey programSurvey) {
        programSurveyList.remove(programSurvey);
        programSurvey.setProgram(null);
    }
}
