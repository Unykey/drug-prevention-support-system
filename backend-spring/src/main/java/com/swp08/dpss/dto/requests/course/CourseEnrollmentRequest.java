package com.swp08.dpss.dto.requests.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentRequest {
    private Long courseId;
    private Long userId;
}
