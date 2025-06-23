package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.requests.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.requests.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.entity.SurveyQuestion;
import com.swp08.dpss.repository.SurveyQuestionRepository;
import com.swp08.dpss.repository.SurveyRepository;
import com.swp08.dpss.service.interfaces.SurveyQuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    private final SurveyQuestionRepository repository;
    private final SurveyRepository surveyRepository;

    @Override
    public SurveyQuestionDto getQuestionById(Long id) {
        SurveyQuestion surveyQuestion = repository.findById(id).get();
        SurveyQuestionDto dto = new SurveyQuestionDto();
        dto.setQuestion(surveyQuestion.getQuestion());
        dto.setType(surveyQuestion.getType());
        dto.setSolution(surveyQuestion.getSolution());
        return dto;
    }

    @Override
    public List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(()-> new EntityNotFoundException("Survey Not Found"));
        List<SurveyQuestion> questions = repository.findBySurvey(survey);

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

    @Override
    public void deleteSurveyQuestionById(Long surveyQuestionId) {
        SurveyQuestion question = repository.findById(surveyQuestionId).orElseThrow(()-> new EntityNotFoundException("Survey Question Not Found"));
        question.getSurvey().removeQuestion(question);
        repository.deleteById(surveyQuestionId);
    }

    @Override
    public SurveyQuestionDto addQuestionToSurvey(Long id, AddSurveyQuestionRequest dto) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

        SurveyQuestion question = new SurveyQuestion();
        question.setQuestion(dto.getQuestion());
        question.setType(dto.getType());
        question.setSolution(dto.getSolution());
        survey.addQuestion(question);

        SurveyQuestion saved = repository.save(question);

        SurveyQuestionDto result = new SurveyQuestionDto();
        result.setId(saved.getId());
        result.setQuestion(saved.getQuestion());
        result.setType(saved.getType());
        result.setSolution(saved.getSolution());
        result.setSurveyId(saved.getSurvey().getId());

        return result;
    }

    @Override
    public SurveyQuestionDto updateQuestion(Long id, UpdateSurveyQuestionRequest request) {
        SurveyQuestion question = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey Question Not Found"));
        question.setQuestion(request.getQuestion());
        question.setType(request.getType());
        question.setSolution(request.getSolution());
        SurveyQuestion saved = repository.save(question);
        SurveyQuestionDto result = new SurveyQuestionDto();
        result.setId(saved.getId());
        result.setQuestion(saved.getQuestion());
        result.setType(saved.getType());
        result.setSolution(saved.getSolution());
        result.setSurveyId(saved.getSurvey().getId());
        return result;
    }
}
