package com.swp08.dpss.service.impls.survey;

import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.enums.SurveyAnswerStatus;
import com.swp08.dpss.enums.SurveyQuestionStatus;
import com.swp08.dpss.mapper.interfaces.SurveyQuestionMapper;
import com.swp08.dpss.repository.survey.SurveyQuestionRepository;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
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

    private final SurveyQuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyQuestionMapper surveyQuestionMapper;

    @Override
    public SurveyQuestionDto getQuestionById(Long id) {
        SurveyQuestion surveyQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        return surveyQuestionMapper.toDto(surveyQuestion);
    }

    @Override
    public List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Not Found"));
        return survey.getQuestions().stream()
                .map(surveyQuestionMapper::toDto)
                .collect(Collectors.toList());
    }

    // ... unchanged delete methods ...

    @Transactional
    @Override
    public SurveyQuestionDto addQuestionToSurvey(Long id, SurveyQuestionRequest dto) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

        SurveyQuestion question = new SurveyQuestion();
        question.setQuestion(dto.getQuestion());
        question.setType(dto.getType());
        question.setSolution(dto.getSolution());
        survey.addQuestion(question);

        return surveyQuestionMapper.toDto(questionRepository.save(question));
    }

    @Transactional
    @Override
    public SurveyQuestionDto updateQuestion(Long id, SurveyQuestionRequest request) {
        SurveyQuestion question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey Question Not Found"));
        question.setQuestion(request.getQuestion());
        question.setType(request.getType());
        question.setSolution(request.getSolution());
        return surveyQuestionMapper.toDto(questionRepository.save(question));
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
                //answer.getUser().removeAnswer(answer);
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
}
