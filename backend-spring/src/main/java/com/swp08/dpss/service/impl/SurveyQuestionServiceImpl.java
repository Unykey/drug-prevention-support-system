package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.entity.SurveyQuestion;
import com.swp08.dpss.repository.SurveyQuestionRepository;
import com.swp08.dpss.service.interfaces.SurveyQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    private final SurveyQuestionRepository repository;

    public SurveyQuestionServiceImpl(SurveyQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId) {
        List<SurveyQuestion> questions = repository.findBySurveyId(surveyId);

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
        repository.deleteById(surveyQuestionId);
    }
}
