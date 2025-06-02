package com.swp08.dpss.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Survey")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private long id;

    @Column (name = "Question", nullable = false)
    private String question;

    @Column (name = "Type", nullable = false)
    private String type;

    //TODO: Add foreign key
}
