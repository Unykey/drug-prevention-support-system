package com.swp08.dpss.dto.requests;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private int status;
    private Long authorId;  // Link to User entity
}