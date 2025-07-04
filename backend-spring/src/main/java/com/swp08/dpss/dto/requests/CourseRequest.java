package com.swp08.dpss.dto.requests;

import com.swp08.dpss.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CourseRequest {
    @NotBlank
    private String title;

    private String description;

    private CourseStatus status = CourseStatus.DRAFT;

    private List<String> targetGroups;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
}