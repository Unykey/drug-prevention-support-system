package com.swp08.dpss.entity.consultant;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "consultant")
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultantId;

    private String consultantName;

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Qualification> qualificationSet;

    @ManyToMany
    @JoinTable(
            name = "consultant_specialization",
            joinColumns = @JoinColumn(name = "consultant_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializationSet;

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Availability> timeSlots;

    private String bio;
}
