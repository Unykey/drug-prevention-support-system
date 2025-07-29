package com.swp08.dpss.dto.responses.course;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentResponse {
    private Long userId;
    private Long courseId;
    private LocalDateTime enrolledAt;
    // You might add user details here if needed, e.g., private String userName;
}
