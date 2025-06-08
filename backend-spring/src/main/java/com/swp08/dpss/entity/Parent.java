package com.swp08.dpss.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parents")
@Getter
@Setter
@NoArgsConstructor
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @Column(nullable = false, length = 100)
    private String parentName;

    @Column(length = 15, unique = true)
    private String parentPhone;

    @Column(length = 100, unique = true)
    private String parentEmail;

    // --- Many-to-Many relationship with User ---
    // 'mappedBy' indicates that the User entity owns the relationship
    // (i.e., the @JoinTable is defined on the User side)
    @ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY)
    private Set<User> user = new HashSet<>();
}
