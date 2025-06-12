package com.swp08.dpss.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SurveyDto {
    private Long id;
    private String name;
    private String description;
    private List<SurveyQuestionDto> questions;
}