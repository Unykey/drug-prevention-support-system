package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SurveyAnswerMapper.class})
public interface SurveyQuestionMapper {

    @Mapping(source = "question_id", target = "id")
    @Mapping(source = "question", target = "question")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "solution", target = "solution")
    @Mapping(source = "survey_id.id", target = "surveyId")
    SurveyQuestionDto toDto(SurveyQuestion entity);

    List<SurveyQuestionDto> toDtoList(List<SurveyQuestion> entities);
}
