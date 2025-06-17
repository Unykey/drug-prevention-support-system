package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.requests.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.entity.SurveyQuestion;
import com.swp08.dpss.repository.SurveyQuestionRepository;
import com.swp08.dpss.repository.SurveyRepository;
import com.swp08.dpss.service.interfaces.SurveyQuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    private final SurveyQuestionRepository repository;
    private final SurveyRepository surveyRepository;

    public SurveyQuestionServiceImpl(SurveyQuestionRepository repository, SurveyRepository surveyRepository) {
        this.repository = repository;
        this.surveyRepository = surveyRepository;
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
    public SurveyQuestionDto addQuestionToSurvey(AddSurveyQuestionRequest dto) {
        Survey survey = surveyRepository.findById(dto.getSurveyId())
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
}
