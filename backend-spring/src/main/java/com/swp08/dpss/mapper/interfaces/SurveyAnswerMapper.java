package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurveyAnswerMapper {

    @Mapping(target = "questionId", source = "question_id.question_id")
    @Mapping(target = "questionText", source = "question_id.question")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "surveyId", source = "survey_id.id")
    SurveyAnswerDto toDto(SurveyAnswer answer);
}
