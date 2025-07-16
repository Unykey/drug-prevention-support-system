package com.swp08.dpss.service.impls.survey;

import com.swp08.dpss.dto.requests.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.mapper.interfaces.SurveyAnswerMapper;
import com.swp08.dpss.repository.survey.SurveyAnswerRepository;
import com.swp08.dpss.repository.survey.SurveyQuestionRepository;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final UserRepository userRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyAnswerMapper surveyAnswerMapper;

    @Autowired
    public SurveyAnswerServiceImpl(SurveyRepository surveyRepository,
        SurveyQuestionRepository surveyQuestionRepository,
        UserRepository userRepository,
        SurveyAnswerRepository surveyAnswerRepository,
        SurveyAnswerMapper surveyAnswerMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyQuestionRepository = surveyQuestionRepository;
        this.userRepository = userRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
        this.surveyAnswerMapper = surveyAnswerMapper;
    }

    @Override
    public List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId) {
        return surveyAnswerRepository.findAll().stream()
                .filter(a -> a.getSurvey_id().getId().equals(surveyId))
                .map(surveyAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SurveyAnswerDto submitAnswer(Long surveyId, Long questionId, SubmitSurveyAnswerRequest request) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found"));
        SurveyQuestion question = surveyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        boolean isCorrect = question.getSolution().trim().equalsIgnoreCase(request.getContent().trim());

        SurveyAnswer answer = question.getAnswers().stream()
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseGet(() -> {
                    SurveyAnswer newAnswer = new SurveyAnswer();
                    survey.addAnswer(newAnswer);
                    question.addAnswer(newAnswer);
                    user.addAnswer(newAnswer);
                    return newAnswer;
                });

        answer.setContent(request.getContent());
        answer.setResultScore(isCorrect ? 10 : 0);

        return surveyAnswerMapper.toDto(surveyAnswerRepository.save(answer));
    }

    @Transactional
    @Override
    public void submitAllAnswers(BulkSubmitSurveyAnswerRequest request) {
        Survey survey = surveyRepository.findById(request.getSurveyId()).orElseThrow();
        User user = userRepository.findById(request.getUserId()).orElseThrow();

        for (BulkSubmitSurveyAnswerRequest.AnswerSubmission answer : request.getAnswers()) {
            SurveyQuestion question = surveyQuestionRepository.findById(answer.getQuestionId()).orElseThrow();
            SurveyAnswer surveyAnswer = new SurveyAnswer();
            surveyAnswer.setContent(answer.getContent());
            survey.addAnswer(surveyAnswer);
            question.addAnswer(surveyAnswer);
            user.addAnswer(surveyAnswer);
            surveyAnswerRepository.save(surveyAnswer);
        }
    }

    // ... unchanged delete methods ...

//    @Transactional
//    @Override
//    public void softDeleteSurveyAnswer(Long id) {
//        SurveyAnswer answer = surveyAnswerRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Survey Answer Not Found"));
//        answer.setStatus(SurveyAnswerStatus.DELETED);
//    }

    @Transactional
    @Override
    public void hardDeleteSurveyAnswer(Long id) {
        SurveyAnswer answer = surveyAnswerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey Answer Not Found with id " + id));
        if (answer.getSurvey_id() != null) {
            answer.getSurvey_id().removeAnswer(answer);
        }
        if (answer.getQuestion_id() != null) {
            answer.getQuestion_id().removeAnswer(answer);
        }
        if (answer.getUser() != null) {
            answer.getUser().removeAnswer(answer);
        }
        surveyAnswerRepository.deleteById(id);
    }
}
