package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.requests.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.requests.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.enums.SurveyAnswerStatus;
import com.swp08.dpss.enums.SurveyQuestionStatus;
import com.swp08.dpss.repository.SurveyQuestionRepository;
import com.swp08.dpss.repository.SurveyRepository;
import com.swp08.dpss.service.interfaces.SurveyQuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    @Autowired
    private final SurveyQuestionRepository questionRepository;
    @Autowired
    private final SurveyRepository surveyRepository;

    @Override
    public SurveyQuestionDto getQuestionById(Long id) {
        SurveyQuestion surveyQuestion = questionRepository.findById(id).get();
        SurveyQuestionDto dto = new SurveyQuestionDto();
        dto.setQuestion(surveyQuestion.getQuestion());
        dto.setType(surveyQuestion.getType());
        dto.setSolution(surveyQuestion.getSolution());
        return dto;
    }

    @Override
    public List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(()-> new EntityNotFoundException("Survey Not Found"));
        List<SurveyQuestion> questions = questionRepository.findBySurvey(survey);

        return questions.stream().map(q -> {
            SurveyQuestionDto dto = new SurveyQuestionDto();
            dto.setId(q.getId());
            dto.setQuestion(q.getQuestion());
            dto.setType(q.getType());
            dto.setSolution(q.getSolution());
            dto.setSurveyId(surveyId);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void hardDeleteSurveyQuestionById(Long surveyQuestionId) {
        SurveyQuestion question = questionRepository.findById(surveyQuestionId).orElseThrow(()-> new EntityNotFoundException("Survey Question Not Found with id" + surveyQuestionId));
        // Remove answers associated with the question
        if (question.getSurvey()!=null) question.getSurvey().removeQuestion(question);
        for (SurveyAnswer answer : question.getAnswers()) {
            if (answer.getSurvey() != null) {
                answer.getSurvey().removeAnswer(answer);
            }
            if (answer.getUser() != null) {
                answer.getUser().removeAnswer(answer);
            }
        }
        question.getAnswers().clear();
        questionRepository.delete(question);
    }

    @Transactional
    @Override
    public void softDeleteSurveyQuestionById(Long surveyQuestionId) {
        SurveyQuestion question = questionRepository.findById(surveyQuestionId).orElseThrow(()-> new EntityNotFoundException("Survey Question Not Found with id" + surveyQuestionId));
        if (question.getSurvey()!=null) question.getSurvey().removeQuestion(question);
        question.setStatus(SurveyQuestionStatus.DELETED);
        for (SurveyAnswer answer : question.getAnswers()) {
            answer.setStatus(SurveyAnswerStatus.DELETED);
        }
    }

    @Transactional
    @Override
    public SurveyQuestionDto addQuestionToSurvey(Long id, AddSurveyQuestionRequest dto) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

        SurveyQuestion question = new SurveyQuestion();
        question.setQuestion(dto.getQuestion());
        question.setType(dto.getType());
        question.setSolution(dto.getSolution());
        survey.addQuestion(question);

        SurveyQuestion saved = questionRepository.save(question);

        SurveyQuestionDto result = new SurveyQuestionDto();
        result.setId(saved.getId());
        result.setQuestion(saved.getQuestion());
        result.setType(saved.getType());
        result.setSolution(saved.getSolution());
        result.setSurveyId(saved.getSurvey().getId());

        return result;
    }

    @Transactional
    @Override
    public SurveyQuestionDto updateQuestion(Long id, UpdateSurveyQuestionRequest request) {
        SurveyQuestion question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey Question Not Found"));
        question.setQuestion(request.getQuestion());
        question.setType(request.getType());
        question.setSolution(request.getSolution());
        SurveyQuestion saved = questionRepository.save(question);
        SurveyQuestionDto result = new SurveyQuestionDto();
        result.setId(saved.getId());
        result.setQuestion(saved.getQuestion());
        result.setType(saved.getType());
        result.setSolution(saved.getSolution());
        result.setSurveyId(saved.getSurvey().getId());
        return result;
    }
}
