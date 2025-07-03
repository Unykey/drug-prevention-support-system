package com.swp08.dpss.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guardian")
@Getter
@Setter
@NoArgsConstructor
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guardianId;

    @Column(nullable = false, length = 100)
    private String guardianName;

    @Column(length = 15, unique = true)
    private String guardianPhone;

    @Column(length = 100, unique = true)
    private String guardianEmail;

    // --- Many-to-Many relationship with User ---
    // 'mappedBy' indicates that the User entity owns the relationship
    // (i.e., the @JoinTable is defined on the User side)
    @ManyToMany(mappedBy = "guardians", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private List<User> user = new ArrayList<>();
    private List<User> user = new ArrayList<>();
}
