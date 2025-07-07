package com.swp08.dpss.dto.requests.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonProgressRequest {
    private Long enrollmentId;
    private Long lessonId;
    private boolean completed;
}
