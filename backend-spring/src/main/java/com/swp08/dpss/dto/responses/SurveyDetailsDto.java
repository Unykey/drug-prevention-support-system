package com.swp08.dpss.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailsDto {
    private Long id;
    private String name;
    private String description;
    private List<SurveyQuestionDto> questions;
}