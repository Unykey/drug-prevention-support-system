package com.swp08.dpss.service.impls.survey;

import com.swp08.dpss.dto.requests.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.UpdateSurveyRequest;
import com.swp08.dpss.dto.responses.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.SurveyAnswerStatus;
import com.swp08.dpss.enums.SurveyQuestionStatus;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.repository.survey.SurveyRepository;
import com.swp08.dpss.service.interfaces.survey.SurveyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    @Override
    public SurveyDetailsDto createSurvey(CreateSurveyRequest request) {
        Survey survey = new Survey();
        survey.setName(request.getName());
        survey.setDescription(request.getDescription());

        return toDto(surveyRepository.save(survey));
    }

    @Override
    public List<SurveyDetailsDto> getAllSurveys() {
        return surveyRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SurveyDetailsDto getSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id " + id));
        return toDto(survey);
    }

    @Override
    public List<SurveyDetailsDto> getSurveysByStatus(SurveyStatus surveyStatus) {
        List<Survey> surveys = surveyRepository.findAllByStatus(surveyStatus);
        return surveys.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SurveyDetailsDto> searchSurveysByName(String name) {
        List<Survey> surveys = surveyRepository.findByNameContainingIgnoreCase(name);
        return surveys.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private SurveyDetailsDto toDto(Survey survey) {
        SurveyDetailsDto dto = new SurveyDetailsDto();
        dto.setId(survey.getId());
        dto.setName(survey.getName());
        dto.setDescription(survey.getDescription());

        dto.setQuestions(
                survey.getQuestions()
                        .stream()
                        .map(q -> {
                            SurveyQuestionDto qDto = new SurveyQuestionDto();
                            qDto.setId(q.getId());
                            qDto.setQuestion(q.getQuestion());
                            qDto.setType(q.getType());
                            qDto.setSolution(q.getSolution());
                            qDto.setSurveyId(survey.getId());
                            return qDto;
                        }).collect(Collectors.toList())
        );

        return dto;
    }

    @Transactional
    @Override
    public void softDeleteSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new RuntimeException("Survey not found with id " + id));
        survey.setStatus(SurveyStatus.DELETED);
        survey.getQuestions().forEach(q -> q.setStatus(SurveyQuestionStatus.DELETED));
        survey.getAnswers().forEach(a -> a.setStatus(SurveyAnswerStatus.DELETED));
    }

    @Transactional
    @Override
    public void hardDeleteSurveyById(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new EntityNotFoundException("Survey Not Found with id " + id);
        }
        surveyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public SurveyDetailsDto updateSurvey(Long id, UpdateSurveyRequest request) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id " + id));

        // Only allow updates if status is DRAFT or PUBLISHED
        if (survey.getStatus() == SurveyStatus.DELETED) {
            throw new IllegalStateException("Cannot edit a deleted survey.");
        }

        survey.setName(request.getName());
        survey.setDescription(request.getDescription());
        survey.setStatus(request.getStatus());

        return toDto(survey);
    }
}
