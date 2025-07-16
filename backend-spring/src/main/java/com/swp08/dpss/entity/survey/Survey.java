package com.swp08.dpss.entity.survey;

import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.course.Course;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.ProgramSurveyRoles;
import com.swp08.dpss.enums.SurveyTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
//@RequiredArgsConstructor
//@AllArgsConstructor //@NoArgsConstructor break and I have no idea why
@NoArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private Long id;

    @Column (name = "Name", nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column (name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column (name = "Type", nullable = false)
    private SurveyTypes type;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status",  nullable = false)
    private SurveyStatus status = SurveyStatus.DRAFT; // default

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by")
    private User user;

    @Column(nullable = false)
    private LocalDate created_at;

    @ManyToMany(mappedBy = "surveys", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<User> userList = new ArrayList<>();

    @ManyToMany(mappedBy = "surveys", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<User> userList = new ArrayList<>();
}
