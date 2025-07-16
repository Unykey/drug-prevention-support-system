package com.swp08.dpss.dto.requests.course;

import com.swp08.dpss.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    @NotBlank
    private String title;

    private String description;

    private CourseStatus status = CourseStatus.DRAFT;

    private List<String> targetGroups;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> surveyId;
}