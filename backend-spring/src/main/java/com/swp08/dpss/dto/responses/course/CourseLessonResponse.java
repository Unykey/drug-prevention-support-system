package com.swp08.dpss.dto.responses.course;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLessonResponse {
    private Long id;
    private String title;
    private String type;
    private String content; // Content link or text
    private int orderIndex;
    private Long courseId; // To indicate which course this lesson belongs to
    private Long surveyId; // Optional survey ID if a lesson is tied to a specific survey
}
