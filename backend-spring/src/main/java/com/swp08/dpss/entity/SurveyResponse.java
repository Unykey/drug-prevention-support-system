package com.swp08.dpss.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table (name = "SurveyResponse")
public class SurveyResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column (name = "SubmittedAt", nullable = false)
    private Date submittedAt;

    @Column (name = "ResultScore")
    private int resultScore;
    //TODO: Add foreign key
}
