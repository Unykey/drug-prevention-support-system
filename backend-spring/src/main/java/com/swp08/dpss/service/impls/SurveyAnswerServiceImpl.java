package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.SurveyAnswerDto;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.enums.SurveyAnswerStatus;
import com.swp08.dpss.repository.SurveyAnswerRepository;
import com.swp08.dpss.repository.SurveyQuestionRepository;
import com.swp08.dpss.repository.SurveyRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.SurveyAnswerService;
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
                .filter(a -> a.getSurvey().getId().equals(surveyId))
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

    @Transactional
    @Override
    public void softDeleteSurveyAnswer(Long id) {
        SurveyAnswer answer = surveyAnswerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey Answer Not Found"));
        answer.setStatus(SurveyAnswerStatus.DELETED);
    }

    @Transactional
    @Override
    public void hardDeleteSurveyAnswer(Long id) {
        SurveyAnswer answer = surveyAnswerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey Answer Not Found with id " + id));
        if (answer.getSurvey() != null) {
            answer.getSurvey().removeAnswer(answer);
        }
        if (answer.getQuestion() != null) {
            answer.getQuestion().removeAnswer(answer);
        }
        if (answer.getUser() != null) {
            answer.getUser().removeAnswer(answer);
        }
        surveyAnswerRepository.deleteById(id);
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

    @Transactional
    @Override
    public void submitAllAnswers(BulkSubmitSurveyAnswerRequest request) {
        Survey survey = surveyRepository.findById(request.getSurveyId()).orElseThrow(()-> new EntityNotFoundException("Survey not found"));
        SurveyQuestion question;
        User user =  userRepository.findById(request.getUserId()).orElseThrow(()-> new EntityNotFoundException("User not found"));
        for (BulkSubmitSurveyAnswerRequest.AnswerSubmission answer : request.getAnswers()) {
            question = surveyQuestionRepository.findById(answer.getQuestionId()).orElseThrow(() -> new EntityNotFoundException("Question not found"));
            SurveyAnswer surveyAnswer = new SurveyAnswer();
            surveyAnswer.setContent(answer.getContent());
            survey.addAnswer(surveyAnswer);
            question.addAnswer(surveyAnswer);
            user.addAnswer(surveyAnswer);
            surveyAnswerRepository.save(surveyAnswer);
        }
    }
}
