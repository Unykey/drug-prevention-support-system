package com.swp08.dpss.dto.responses.course;

import com.swp08.dpss.enums.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private CourseStatus status;
    private List<String> targetGroups;
    private LocalDate startDate;
    private LocalDate endDate;
}
