package com.swp08.dpss.dto.requests.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseLessonRequest {
    private String title;
    private String type; // READING, VIDEO, QUIZ
    private String content; // text or URL
    private int orderIndex;
    private Long surveyId; // optional (nullable)
}
