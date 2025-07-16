package com.swp08.dpss.dto.requests.course;

import com.swp08.dpss.entity.course.CourseEnrollmentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonProgressRequest {
    private CourseEnrollmentId enrollmentId;
    private Long lessonId;
    private boolean completed;
}
