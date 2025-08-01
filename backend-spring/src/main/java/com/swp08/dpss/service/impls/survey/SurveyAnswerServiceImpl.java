package com.swp08.dpss.service.impls.survey;

import com.swp08.dpss.dto.requests.survey.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.entity.survey.AnswerOption;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.entity.survey.SurveyAnswer;
import com.swp08.dpss.entity.survey.SurveyQuestion;
import com.swp08.dpss.enums.QuestionTypes;
import jakarta.persistence.EntityNotFoundException;
import com.swp08.dpss.mapper.interfaces.survey.SurveyAnswerMapper;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.repository.survey.AnswerOptionRepository;
import com.swp08.dpss.repository.survey.SurveyAnswerRepository;
import com.swp08.dpss.repository.survey.SurveyQuestionRepository;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
// import java.util.Map; // No longer needed for BulkSubmitSurveyAnswerRequest directly in this way
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j // For logging
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final UserRepository userRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final SurveyAnswerMapper surveyAnswerMapper;

    @Override
    @Transactional
    public SurveyAnswerDto submitAnswer(Long surveyId, Long questionId, SubmitSurveyAnswerRequest answerRequest, String userEmail) {
        // Fetch entities
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with ID: " + surveyId));
        SurveyQuestion question = surveyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Question not found with ID: " + questionId));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        // Check if user has already answered this question for this survey
        Optional<SurveyAnswer> existingAnswer = surveyAnswerRepository.findByQuestionAndUser(question, user);
        SurveyAnswer surveyAnswer;

        if (existingAnswer.isPresent()) {
            surveyAnswer = existingAnswer.get();
            log.info("Updating existing answer for user {} on question {}", userEmail, questionId);
        } else {
            surveyAnswer = new SurveyAnswer();
            surveyAnswer.setSurvey(survey);
            surveyAnswer.setQuestion(question);
            surveyAnswer.setUser(user);
            log.info("Creating new answer for user {} on question {}", userEmail, questionId);
        }

        Integer score = 0; // Initialize score

        // Handle answer based on question type
        if (question.getType() == QuestionTypes.YN ||
                question.getType() == QuestionTypes.MCQ ||
                question.getType() == QuestionTypes.MCMC) {

            AnswerOption selectedOption = question.getOptions().stream()
                    .filter(opt -> opt.getContent() != null && opt.getContent().trim().equalsIgnoreCase(answerRequest.getContent().trim()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(
                            "AnswerOption with content '" + answerRequest.getContent() + "' not found for this question."));

            System.out.println("Question ID: " + question.getQuestion_id());
            System.out.println("AnswerRequest content: " + answerRequest.getContent());

            for (AnswerOption opt : question.getOptions()) {
                System.out.println("Comparing: option = [" + opt.getContent() + "] vs input = [" + answerRequest.getContent() + "]");
                System.out.println("Equals: " + opt.getContent().equals(answerRequest.getContent()));
            }

            System.out.println("/////");

            System.out.println(answerRequest.getOptionId());
            System.out.println(answerRequest.getContent());
            System.out.println(selectedOption.getContent());
            System.out.println(selectedOption.isCorrect());
            System.out.println(selectedOption.getQuestion().getQuestion());
            System.out.println(selectedOption.getQuestion().getQuestion_id() + " " + question.getQuestion_id());
            System.out.println(selectedOption.getQuestion() + " " + question);
            System.out.println(question.getQuestion());

            // Validate that the selected option belongs to the question
            if (!selectedOption.getQuestion().getQuestion_id().equals(question.getQuestion_id())) {
                throw new IllegalArgumentException("Selected option does not belong to the provided question.");
            }

            System.out.println(answerRequest.getContent() + "TEST1");
            surveyAnswer.setOption(selectedOption); // Set the selected option
            surveyAnswer.setContent(null); // Clear content if it's an option-based answer
            System.out.println(answerRequest.getContent() + "TEST2");
            // Calculate score for quiz types
            if (selectedOption.isCorrect()) {
                score = 1;
            }

        } else if (question.getType() == QuestionTypes.NUMBER ||
                question.getType() == QuestionTypes.SCALE) {
            if (answerRequest.getContent() == null || answerRequest.getContent().isBlank()) {
                throw new IllegalArgumentException("Content must be provided for questions of type " + question.getType());
            }
            System.out.println(answerRequest.getContent() + "TEST");
            surveyAnswer.setContent(answerRequest.getContent()); // Set free-text content
            surveyAnswer.setOption(null); // Clear option if it's a free-text answer

            score = 0; // Default or placeholder score
        } else {
            throw new IllegalArgumentException("Unsupported question type: " + question.getType());
        }

        surveyAnswer.setResultScore(score);
        surveyAnswer.setSubmittedAt(LocalDateTime.now()); // Update timestamp on resubmission

        SurveyAnswer savedAnswer = surveyAnswerRepository.save(surveyAnswer);

        // question.addAnswer(savedAnswer); // Re-commented, rely on cascade or explicit bidirectional management if needed.
        return surveyAnswerMapper.toDto(savedAnswer);
    }

    @Override
    public List<SurveyAnswerDto> getAnswersBySurveyId(Long surveyId) {
        // You mentioned `findAllBySurvey_id` in your last message for SurveyAnswerRepository.
        // I will use that for consistency with what you implied works for you.
        // If it's `findBySurvey_Id` as I previously suggested, please adjust.
        return surveyAnswerRepository.findAllBySurvey_id(surveyId)
                .stream()
                .map(surveyAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SurveyAnswerDto> getAnswersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        // Similarly, using `findAllByUser` based on your last provided code.
        return surveyAnswerRepository.findAllByUser(user)
                .stream()
                .map(surveyAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SurveyAnswerDto getAnswerById(Long answerId) {
        SurveyAnswer answer = surveyAnswerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Answer not found with ID: " + answerId));
        return surveyAnswerMapper.toDto(answer);
    }

    @Override
    @Transactional
    public void deleteAnswer(Long answerId) {
        SurveyAnswer answer = surveyAnswerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Survey Answer not found with ID: " + answerId));
        // If bidirectional management is crucial and not fully covered by cascade/orphanRemoval
        // if (answer.getQuestion_id() != null) {
        //     answer.getQuestion_id().removeAnswer(answer);
        // }
        surveyAnswerRepository.delete(answer);
    }

    @Override
    @Transactional
    public void submitAllAnswers(Long surveyId, BulkSubmitSurveyAnswerRequest request, String userEmail) {
        // IMPORTANT: The request has `userId` and `surveyId` fields, but the endpoint also takes `surveyId` and `userEmail` (from @AuthenticationPrincipal).
        // It's best practice to trust the authenticated user details for security.
        // We'll use the userEmail from `AuthenticationPrincipal` for the User lookup.
        // We'll use the `surveyId` from the path variable for the survey context.
        // The `request.getSurveyId()` and `request.getUserId()` are redundant if passed in path/principal.
        // For now, I'll prioritize the path variable `surveyId` and `userEmail` from principal.

        // Fetch user once
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        // Fetch survey once (if needed, or just validate)
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with ID: " + surveyId));

        if (request.getAnswers() != null && !request.getAnswers().isEmpty()) {
            for (BulkSubmitSurveyAnswerRequest.AnswerSubmission submission : request.getAnswers()) {
                Long questionId = submission.getQuestionId();
                String content = submission.getContent();

                // Create a SubmitSurveyAnswerRequest for the single submitAnswer method
                SubmitSurveyAnswerRequest singleAnswerRequest = new SubmitSurveyAnswerRequest();
                // We need to determine if 'content' or 'optionId' should be set for the singleAnswerRequest.
                // This depends on the question type. We'll need to fetch the question to know its type.
                SurveyQuestion question = surveyQuestionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Survey Question not found with ID: " + questionId + " for bulk submission."));

                if (question.getType() == QuestionTypes.MCQ ||
                        question.getType() == QuestionTypes.MCMC ||
                        question.getType() == QuestionTypes.YN) {
                    // Find the option by content. This assumes option content is unique per question.
                    // If not, you might need a more robust way (e.g., frontend sends optionId).
                    AnswerOption selectedOption = answerOptionRepository.findByQuestionAndContent(question, content)
                            .orElseThrow(() -> new EntityNotFoundException("Option with content '" + content + "' not found for question ID: " + questionId));
                    singleAnswerRequest.setOptionId(selectedOption.getId());
                    singleAnswerRequest.setContent(null); // Ensure content is null for option-based
                } else if (question.getType() == QuestionTypes.NUMBER ||
                        question.getType() == QuestionTypes.SCALE) {
                    singleAnswerRequest.setContent(content);
                    singleAnswerRequest.setOptionId(null); // Ensure optionId is null for content-based
                } else {
                    throw new IllegalArgumentException("Unsupported question type for bulk submission: " + question.getType());
                }

                // Call the single submitAnswer logic for each answer
                // We're passing the userEmail and surveyId from the outer scope, assuming they are consistent.
                submitAnswer(surveyId, questionId, singleAnswerRequest, userEmail);
            }
        } else {
            log.warn("Attempted to submit all answers for survey {} by user {} but no answers were provided in the request.", surveyId, userEmail);
        }
    }
}