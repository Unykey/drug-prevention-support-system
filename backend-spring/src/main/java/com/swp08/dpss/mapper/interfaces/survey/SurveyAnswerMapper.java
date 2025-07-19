package com.swp08.dpss.mapper.interfaces.survey;

import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurveyAnswerMapper {

    @Mapping(target = "questionId", source = "question.question_id")
    @Mapping(target = "questionText", source = "question.question")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "surveyId", source = "survey.id")
    // Map the new 'option' field's ID
    @Mapping(target = "optionId", source = "option.id")
    // Keep content mapping, as it's for free-text answers now
    @Mapping(target = "content", source = "content")
    SurveyAnswerDto toDto(SurveyAnswer answer);

    // You might also need a method to map SubmitSurveyAnswerRequest to SurveyAnswer entity
    // However, the service logic for SubmitSurveyAnswerRequest is complex due to
    // conditional logic based on question type (optionId vs. content) and user association.
    // It's often better to handle this conversion manually in the service or use a dedicated builder.
}