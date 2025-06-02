package com.swp08.dpss.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "Survey")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor //@NoArgsConstructor break and I have no idea why
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private long id;

    @Column (name = "Name", nullable = false, columnDefinition = "nvarchar(50)")
    private String name;

    @Column (name = "Description")
    private String description;
}
