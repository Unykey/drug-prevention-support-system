package com.swp08.dpss.service.impls.survey;

import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.UpdateSurveyRequest;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.entity.survey.Survey;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.enums.SurveyTypes;
import com.swp08.dpss.mapper.interfaces.SurveyMapper;
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
    private final SurveyMapper surveyMapper;

    @Transactional
    @Override
    public SurveyDetailsDto createSurvey(CreateSurveyRequest request) {
        Survey survey = new Survey();
        survey.setName(request.getName());
        survey.setDescription(request.getDescription());
        //TODO: Patch the type
//        if (request.getSurveyType() != null) survey.setType(request.getSurveyType());
        if (request.getSurveyStatus() != null) survey.setStatus(request.getSurveyStatus());
        return surveyMapper.toDto(surveyRepository.save(survey));
    }

    @Override
    public List<SurveyDetailsDto> getAllSurveys() {
        return surveyRepository.findAll()
                .stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SurveyDetailsDto getSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id " + id));
        return surveyMapper.toDto(survey);
    }

    @Override
    public List<SurveyDetailsDto> getSurveysByStatus(SurveyStatus surveyStatus) {
        return surveyRepository.findAllByStatus(surveyStatus)
                .stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SurveyDetailsDto> searchSurveysByName(String name) {
        return surveyRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SurveyDetailsDto updateSurvey(Long id, UpdateSurveyRequest request) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id " + id));
        if (survey.getStatus() == SurveyStatus.DELETED)
            throw new IllegalStateException("Cannot edit a deleted survey.");
        survey.setName(request.getName());
        survey.setDescription(request.getDescription());
        survey.setType(request.getType());
        survey.setStatus(request.getStatus());
        return surveyMapper.toDto(surveyRepository.save(survey));
    }

    // ... unchanged delete methods ...

    @Override
    public List<SurveyDetailsDto> getSurveysByTypeAndStatus(SurveyTypes type, SurveyStatus status) {
        return surveyRepository.findAllByStatusAndType(status, type)
                .stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SurveyDetailsDto> getSurveysByType(SurveyTypes type) {
        return surveyRepository.findByType(type)
                .stream()
                .map(surveyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void softDeleteSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new RuntimeException("Survey not found with id " + id));
        survey.setStatus(SurveyStatus.DELETED);
    }

    @Transactional
    @Override
    public void hardDeleteSurveyById(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new EntityNotFoundException("Survey Not Found with id " + id);
        }
        surveyRepository.deleteById(id);
    }

}
