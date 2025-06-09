package com.swp08.dpss.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int status;
    private String authorName;  // Derived from User entity
}