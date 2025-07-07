package com.swp08.dpss.dto.responses.course;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseEnrollmentResponse {
    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDateTime enrolledAt;
}
