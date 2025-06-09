package com.swp08.dpss.entity;

import com.swp08.dpss.enums.PostStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Post {
    final static int DRAFT = 0;
    final static int PUBLISHED = 1;
    final static int DELETED = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private long id;

    @Column (name = "Title", nullable = false)
    private String title;

    @Column (name = "Content")
    private String content;

    @Column (name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column (name = "Status", nullable = false, length = 1)
    private PostStatus status;

    @ManyToOne
    @JoinColumn (name = "Author")
    private User author;
}
