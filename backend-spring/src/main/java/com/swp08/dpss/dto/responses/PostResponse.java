package com.swp08.dpss.dto.responses;

import com.swp08.dpss.enums.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private PostStatus status;
    private String authorName;  // Derived from User entity
}