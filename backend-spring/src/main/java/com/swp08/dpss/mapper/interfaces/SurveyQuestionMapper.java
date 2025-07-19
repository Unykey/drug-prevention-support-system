package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SurveyAnswerMapper.class}) // Note: SurveyQuestionDto typically doesn't need SurveyAnswerMapper
public interface SurveyQuestionMapper {

    @Mapping(source = "question_id", target = "id")
    @Mapping(source = "question", target = "question")
    @Mapping(source = "type", target = "type") // DTO's 'type' field should now be QuestionTypes enum
    @Mapping(source = "solution", target = "solution")
    @Mapping(source = "value", target = "value")
    // Assuming SurveyQuestion.survey_id field is renamed to SurveyQuestion.survey
    // And Survey has a primary key named 'id'
    @Mapping(source = "survey_id.id", target = "surveyId")
    SurveyQuestionDto toDto(SurveyQuestion entity);

    List<SurveyQuestionDto> toDtoList(List<SurveyQuestion> entities);
}