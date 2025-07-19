package com.swp08.dpss.entity.consultant;

import com.swp08.dpss.entity.client.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "consultant")
@Getter
@Setter
@NoArgsConstructor
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultantId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Qualification> qualificationSet;

    @ManyToMany
    @JoinTable(
            name = "consultant_specialization",
            joinColumns = @JoinColumn(name = "consultant_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializationSet;

    @ManyToMany
    @JoinTable(
            name = "consultant_availability",
            joinColumns = @JoinColumn(name = "consultant_id"),
            inverseJoinColumns = @JoinColumn(name = "availability_id")
    )
    private Set<Availability> timeSlots;

    @Column(length = 255)
    private String bio;

    private String profilePicture;
}
