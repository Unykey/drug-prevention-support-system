package com.swp08.dpss.dto.responses.course;

import com.swp08.dpss.entity.course.TargetGroup;
import com.swp08.dpss.enums.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private CourseStatus status;
    private Set<TargetGroup> targetGroups;
    private LocalDate startDate;
    private LocalDate endDate;
}
