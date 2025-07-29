package com.swp08.dpss.dto.responses.course;

import com.swp08.dpss.entity.course.TargetGroup;
import com.swp08.dpss.enums.CourseStatus;
import com.swp08.dpss.enums.TargetGroupName;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private CourseStatus status;
    private boolean isPublished;
    private LocalDateTime createdAt; // Assuming you have this field in Course entity

    // Flattened relationships:
    private Set<TargetGroupName> targetGroups; // DTO will expose enum names directly
    private List<CourseLessonResponse> lessons; // Nested DTO for lessons
    private List<CourseEnrollmentResponse> enrollments; // Nested DTO for enrollments
    private List<CourseSurveyResponse> courseSurveys; // Nested DTO for course surveys
}
