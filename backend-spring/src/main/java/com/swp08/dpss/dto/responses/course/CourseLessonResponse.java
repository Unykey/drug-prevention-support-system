package com.swp08.dpss.dto.responses.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseLessonResponse {
    private Long id;
    private String title;
    private String type;         // READING, VIDEO, QUIZ
    private String content;      // link or text
    private int orderIndex;
    private Long courseId;
    private Long surveyId;       // optional
}
