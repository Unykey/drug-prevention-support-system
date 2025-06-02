package com.swp08.dpss.entity;

import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
//@Table(name="\"User\"")
@Table(name = "AppUser")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50)
    String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Roles role;

    @Enumerated(EnumType.STRING)
    Genders gender;

    @Column(nullable = false)
    LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, unique = true)
    String phone;
}
