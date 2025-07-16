package com.swp08.dpss.dto.responses.course;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessonProgressResponse {
    private Long id;
    private Long surveyId;
    private Long userId;
    private Long lessonId;
    private boolean completed;
    private LocalDateTime completedAt;
}
