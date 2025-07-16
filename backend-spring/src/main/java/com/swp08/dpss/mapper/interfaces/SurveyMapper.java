package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.entity.survey.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { SurveyQuestionMapper.class })
public interface SurveyMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "type", target = "surveyType")
    @Mapping(source = "status", target = "surveyStatus")
    @Mapping(source = "questions", target = "questions")
    SurveyDetailsDto toDto(Survey survey);

    List<SurveyDetailsDto> toDtoList(List<Survey> surveys);
}
