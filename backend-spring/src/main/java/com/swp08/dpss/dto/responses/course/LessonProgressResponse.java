package com.swp08.dpss.dto.responses.course;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonProgressResponse {
    private Long id;
    private Long courseId;
    private Long userId;
    private Long lessonId;
    private boolean completed;
    private LocalDateTime completedAt;

}
