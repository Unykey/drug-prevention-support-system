package com.swp08.dpss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private long id;

    @Column (name = "Title", nullable = false)
    private String title;

    @Column (name = "Content", nullable = false)
    private String content;

    @Column (name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn (name = "Author")
    private User author;
}
