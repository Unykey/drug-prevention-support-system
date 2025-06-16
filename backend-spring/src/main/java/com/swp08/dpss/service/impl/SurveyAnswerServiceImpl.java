package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.responses.SurveyAnswerDto;
import com.swp08.dpss.entity.SurveyAnswer;
import com.swp08.dpss.repository.SurveyAnswerRepository;
import com.swp08.dpss.service.interfaces.SurveyAnswerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    private final SurveyAnswerRepository repository;

    public SurveyAnswerServiceImpl(SurveyAnswerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId) {
        List<SurveyAnswer> answers = ((List<SurveyAnswer>) repository.findAll()) // repository returns Iterable
                .stream()
                .filter(a -> a.getSurvey().getId() == surveyId)
                .collect(Collectors.toList());

        return answers.stream().map(a -> {
            SurveyAnswerDto dto = new SurveyAnswerDto();
            dto.setId(a.getId());
            dto.setContent(a.getContent());
            dto.setResultScore(a.getResultScore());
            dto.setSubmittedAt(a.getSubmittedAt());

            if (a.getQuestion() != null) {
                dto.setQuestionId(a.getQuestion().getId());
                dto.setQuestionText(a.getQuestion().getQuestion());
            }

            if (a.getUser() != null) {
                dto.setUserId(a.getUser().getId());
                dto.setUserName(a.getUser().getName());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteSurveyAnswer(Long surveyAnswerId) {
        repository.deleteById(surveyAnswerId);
    }
}
