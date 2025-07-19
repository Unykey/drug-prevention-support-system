package com.swp08.dpss.service.impls.survey; // Corrected package name

import com.swp08.dpss.dto.requests.survey.QuestionOptionRequest;
import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.QuestionOption;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.enums.QuestionTypes;
import jakarta.persistence.EntityNotFoundException; // Corrected import to jakarta.persistence.EntityNotFoundException
import com.swp08.dpss.mapper.interfaces.survey.QuestionOptionMapper;
import com.swp08.dpss.mapper.interfaces.survey.SurveyQuestionMapper;
import com.swp08.dpss.repository.survey.QuestionOptionRepository;
import com.swp08.dpss.repository.survey.SurveyQuestionRepository;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Corrected slf4j package, should be org.slf4j.Slf4j
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j // For logging
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final QuestionOptionRepository questionOptionRepository; // Inject new repo
    private final SurveyQuestionMapper surveyQuestionMapper;
    private final QuestionOptionMapper questionOptionMapper; // Inject new mapper

    @Override
    @Transactional
    public SurveyQuestionDto addQuestionToSurvey(Long surveyId, SurveyQuestionRequest questionRequest) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with ID: " + surveyId));

        SurveyQuestion question = surveyQuestionMapper.toEntity(questionRequest);
        question.setSurvey(survey); // Set the relationship

        // Handle QuestionOptions
        if (questionRequest.getOptions() != null && !questionRequest.getOptions().isEmpty()) {
            // Validate options for MCQ/MCMC
            if (question.getType() == QuestionTypes.MCQ || question.getType() == QuestionTypes.MCMC) {
                if (questionRequest.getOptions().size() < 2) { // Typically need at least 2 options for multiple choice
                    throw new IllegalArgumentException("MCQ/MCMC questions must have at least 2 options.");
                }
                // Additional validation: ensure only one isCorrect for MCQ, or at least one for MCMC
                if (question.getType() == QuestionTypes.MCQ) {
                    long correctOptions = questionRequest.getOptions().stream().filter(QuestionOptionRequest::isCorrect).count();
                    if (correctOptions != 1) {
                        throw new IllegalArgumentException("MCQ type questions must have exactly one correct option.");
                    }
                } else if (question.getType() == QuestionTypes.MCMC) {
                    long correctOptions = questionRequest.getOptions().stream().filter(QuestionOptionRequest::isCorrect).count();
                    if (correctOptions < 1) { // At least one correct option
                        throw new IllegalArgumentException("MCMC type questions must have at least one correct option.");
                    }
                }
            } else if (question.getType() == QuestionTypes.YN || question.getType() == QuestionTypes.SCALE || question.getType() == QuestionTypes.NUMBER) {
                // These types typically don't have explicit options, or if they do, they are fixed (e.g., Yes/No always has 2 options)
                // If options are provided for these types, you might want to throw an error or handle it specifically.
                // For now, if options are provided for these types, they will be saved but might not be used.
                // Consider adding specific validation here. E.g., for YN, enforce exactly two options "Yes" and "No".
                if (!questionRequest.getOptions().isEmpty()) {
                    log.warn("Options provided for question type {} which typically does not use options. Options will be stored but may not be used for logic.", question.getType());
                }
            }

            List<QuestionOption> options = new ArrayList<>();
            for (QuestionOptionRequest optionRequest : questionRequest.getOptions()) {
                QuestionOption option = questionOptionMapper.toEntity(optionRequest);
                option.setQuestion(question); // Set the bidirectional relationship
                options.add(option);
            }
            question.setOptions(options); // Set the list of options
        } else {
            // If no options are provided but the question type requires them
            if (question.getType() == QuestionTypes.MCQ || question.getType() == QuestionTypes.MCMC || question.getType() == QuestionTypes.YN) {
                throw new IllegalArgumentException("Questions of type " + question.getType() + " must have options provided.");
            }
        }

        SurveyQuestion savedQuestion = surveyQuestionRepository.save(question);
        return surveyQuestionMapper.toDto(savedQuestion);
    }

    @Override
    public SurveyQuestionDto getQuestionById(Long questionId) {
        SurveyQuestion question = surveyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Question not found with ID: " + questionId));
        return surveyQuestionMapper.toDto(question);
    }

    @Override
    public List<SurveyQuestionDto> getQuestionsBySurveyId(Long surveyId) {
        return surveyQuestionRepository.findAllBySurveyId(surveyId)
                .stream()
                .map(surveyQuestionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SurveyQuestionDto updateQuestion(Long questionId, SurveyQuestionRequest questionRequest) {
        SurveyQuestion existingQuestion = surveyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Question not found with ID: " + questionId));

        // Update basic fields
        existingQuestion.setQuestion(questionRequest.getQuestion());
        existingQuestion.setType(questionRequest.getType());

        // Validate options for MCQ/MCMC during update
        if (questionRequest.getOptions() != null && !questionRequest.getOptions().isEmpty()) {
            if (questionRequest.getType() == QuestionTypes.MCQ || questionRequest.getType() == QuestionTypes.MCMC) {
                if (questionRequest.getOptions().size() < 2) {
                    throw new IllegalArgumentException("MCQ/MCMC questions must have at least 2 options.");
                }
                if (questionRequest.getType() == QuestionTypes.MCQ) {
                    long correctOptions = questionRequest.getOptions().stream().filter(QuestionOptionRequest::isCorrect).count();
                    if (correctOptions != 1) {
                        throw new IllegalArgumentException("MCQ type questions must have exactly one correct option.");
                    }
                } else if (questionRequest.getType() == QuestionTypes.MCMC) {
                    long correctOptions = questionRequest.getOptions().stream().filter(QuestionOptionRequest::isCorrect).count();
                    if (correctOptions < 1) {
                        throw new IllegalArgumentException("MCMC type questions must have at least one correct option.");
                    }
                }
            } else if (questionRequest.getType() == QuestionTypes.YN || questionRequest.getType() == QuestionTypes.SCALE || questionRequest.getType() == QuestionTypes.NUMBER) {
                if (!questionRequest.getOptions().isEmpty()) {
                    log.warn("Options provided for question type {} during update, which typically does not use options. Options will be stored but may not be used for logic.", questionRequest.getType());
                }
            }
        } else {
            // If no options are provided but the updated question type requires them
            if (questionRequest.getType() == QuestionTypes.MCQ || questionRequest.getType() == QuestionTypes.MCMC || questionRequest.getType() == QuestionTypes.YN) {
                throw new IllegalArgumentException("Questions of type " + questionRequest.getType() + " must have options provided.");
            }
        }


        // Handle options: This is the complex part.
        List<QuestionOption> optionsToRemove = new ArrayList<>();
        // Identify options to remove (those currently on the question but not in the request)
        existingQuestion.getOptions().forEach(existingOption -> {
            boolean foundInRequest = questionRequest.getOptions() != null &&
                    questionRequest.getOptions().stream()
                            .anyMatch(reqOption -> reqOption.getContent().equals(existingOption.getContent())); // Match by content
            if (!foundInRequest) {
                optionsToRemove.add(existingOption);
            }
        });

        // Remove identified options
        existingQuestion.getOptions().removeAll(optionsToRemove);
        questionOptionRepository.deleteAll(optionsToRemove);


        // Add or update options
        if (questionRequest.getOptions() != null) {
            for (QuestionOptionRequest reqOption : questionRequest.getOptions()) {
                Optional<QuestionOption> existingOpt = existingQuestion.getOptions().stream()
                        .filter(opt -> opt.getContent().equals(reqOption.getContent())) // Match by content, consider ID for updates if available
                        .findFirst();

                if (existingOpt.isPresent()) {
                    // Update existing option (e.g., isCorrect status)
                    existingOpt.get().setContent(reqOption.getContent()); // Update content just in case
                    existingOpt.get().setCorrect(reqOption.isCorrect());
                } else {
                    // Add new option
                    QuestionOption newOption = questionOptionMapper.toEntity(reqOption);
                    newOption.setQuestion(existingQuestion); // Set relationship
                    existingQuestion.getOptions().add(newOption);
                }
            }
        }

        SurveyQuestion updatedQuestion = surveyQuestionRepository.save(existingQuestion);
        return surveyQuestionMapper.toDto(updatedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        if (!surveyQuestionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Survey Question not found with ID: " + questionId);
        }
        surveyQuestionRepository.deleteById(questionId);
    }
}