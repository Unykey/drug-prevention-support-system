package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurveyAnswerMapper {

    // Assuming SurveyAnswer.question_id field is renamed to SurveyAnswer.question
    // And SurveyQuestion has a primary key named 'question_id'
    @Mapping(target = "questionId", source = "question_id.question_id")
    // Assuming SurveyAnswer.question_id field is renamed to SurveyAnswer.question
    // And SurveyQuestion has a field named 'question' for its text
    @Mapping(target = "userId", source = "user.id")
    // Assuming SurveyAnswer.survey_id field is renamed to SurveyAnswer.survey
    // And Survey has a primary key named 'id'
    @Mapping(target = "surveyId", source = "survey_id.id")
    SurveyAnswerDto toDto(SurveyAnswer answer);
}