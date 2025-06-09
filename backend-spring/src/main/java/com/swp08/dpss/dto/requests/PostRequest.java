package com.swp08.dpss.dto.requests;

import com.swp08.dpss.enums.PostStatus;
import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private PostStatus status;
    private Long authorId;  // Link to User entity
}