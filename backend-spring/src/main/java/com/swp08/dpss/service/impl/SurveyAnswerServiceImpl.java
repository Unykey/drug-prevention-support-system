package com.swp08.dpss.service.impl;

import com.swp08.dpss.dto.responses.SurveyAnswerDto;
import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.entity.SurveyAnswer;
import com.swp08.dpss.entity.SurveyQuestion;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.repository.SurveyAnswerRepository;
import com.swp08.dpss.repository.SurveyQuestionRepository;
import com.swp08.dpss.repository.SurveyRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.SurveyAnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swp08.dpss.dto.requests.SubmitSurveyAnswerRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final UserRepository userRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    public SurveyAnswerServiceImpl(SurveyRepository surveyRepository, SurveyQuestionRepository surveyQuestionRepository, UserRepository userRepository, SurveyAnswerRepository surveyAnswerRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyQuestionRepository = surveyQuestionRepository;
        this.userRepository = userRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
    }

    @Override
    public List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId) {
        List<SurveyAnswer> answers = ((List<SurveyAnswer>) surveyAnswerRepository.findAll()) // repository returns Iterable
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
                //dto.setQuestionId(a.getQuestion().getId());
                dto.setQuestionText(a.getQuestion().getQuestion());
            }

            if (a.getUser() != null) {
                //dto.setUserId(a.getUser().getId());
                dto.setUserName(a.getUser().getName());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteSurveyAnswer(Long surveyAnswerId) {
        SurveyAnswer answer = (SurveyAnswer) surveyAnswerRepository.findById(surveyAnswerId).orElse(null);
        answer.getSurvey().removeAnswer(answer);
        answer.getQuestion().removeAnswer(answer);
        answer.getUser().removeAnswer(answer);
        surveyAnswerRepository.deleteById(surveyAnswerId);
    }

    @Override
    public SurveyAnswerDto submitAnswer(SubmitSurveyAnswerRequest request) {
        Survey survey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));
        SurveyQuestion question = surveyQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        boolean isCorrect = question.getSolution().trim().equalsIgnoreCase(request.getContent().trim());

        SurveyAnswer answer = new SurveyAnswer();
        answer.setContent(request.getContent());
        answer.setResultScore(isCorrect ? 10 : 0);
        survey.addAnswer(answer);
        question.addAnswer(answer);
        user.addAnswer(answer);

        SurveyAnswer saved = surveyAnswerRepository.save(answer);

        SurveyAnswerDto dto = new SurveyAnswerDto();
        dto.setId(saved.getId());
        dto.setContent(saved.getContent());
        dto.setResultScore(saved.getResultScore());
        dto.setSubmittedAt(saved.getSubmittedAt());
        //dto.setQuestionId(question.getId());
        dto.setQuestionText(question.getQuestion());
        //dto.setUserId(user.getId());
        dto.setUserName(user.getName());

        return dto;
    }

}
