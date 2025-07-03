package com.swp08.dpss.entity;

import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.enums.User_Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String password;    

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
}
