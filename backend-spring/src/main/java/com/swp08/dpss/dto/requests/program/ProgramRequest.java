package com.swp08.dpss.dto.requests.program;

import com.swp08.dpss.dto.requests.course.CourseSurveyRequest;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.consultant.Consultant;
import com.swp08.dpss.entity.course.TargetGroup;
import com.swp08.dpss.enums.ProgramStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramRequest {
    @NotBlank
    private String title;

    private String description;

    private List<ProgramSurveyRequest> programSurveys;


    private Set<Consultant> hostedBy;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String location;

    public ProgramRequest(String title, String description, Set<Consultant> hostedBy, LocalDateTime startDate, LocalDateTime endDate, String location) {
        this.title = title;
        this.description = description;
        this.hostedBy = hostedBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }
}
