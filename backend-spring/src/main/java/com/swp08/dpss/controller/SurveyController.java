package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.survey.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.UpdateSurveyRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import com.swp08.dpss.service.interfaces.survey.SurveyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyAnswerService surveyAnswerService;

    @Autowired
    public SurveyController(SurveyService surveyService, SurveyQuestionService surveyQuestionService, SurveyAnswerService surveyAnswerService) {
        this.surveyService = surveyService;
        this.surveyQuestionService = surveyQuestionService;
        this.surveyAnswerService = surveyAnswerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SurveyDetailsDto>> createSurvey(
            @Valid @RequestBody CreateSurveyRequest request) {
        SurveyDetailsDto created = surveyService.createSurvey(request);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, created, "Survey created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SurveyDetailsDto>>> getPublishedSurveys() {
        return ResponseEntity.ok(new ApiResponse<>(true, surveyService.getSurveysByStatus(SurveyStatus.PUBLISHED), "Published surveys retrieved"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SurveyDetailsDto>>> getAllSurveys(
            @RequestParam(required = false) SurveyStatus status,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<SurveyDetailsDto> result = (status != null)
                ? surveyService.getSurveysByStatus(status)
                : surveyService.getAllSurveys();
        return ResponseEntity.ok(new ApiResponse<>(true, result, "Surveys retrieved"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SurveyDetailsDto>> getSurveyById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, surveyService.getSurveyById(id), "Survey retrieved"));
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<ApiResponse<List<SurveyQuestionDto>>> getQuestionsBySurveyId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, surveyQuestionService.getQuestionsBySurveyId(id), "Survey questions retrieved"));
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<ApiResponse<List<SurveyAnswerDto>>> getAnswersBySurveyId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, surveyAnswerService.getAnswersBySurveyId(id), "Survey answers retrieved"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SurveyDetailsDto>>> searchSurveys(@RequestParam String keyword) {
        return ResponseEntity.ok(new ApiResponse<>(true, surveyService.searchSurveysByName(keyword), "Survey search results"));
    }

    @PostMapping("/{id}/addquestion")
    public ResponseEntity<ApiResponse<SurveyQuestionDto>> addQuestionToSurvey(
            @RequestBody AddSurveyQuestionRequest questionDto, @PathVariable Long id) {
        SurveyQuestionDto saved = surveyQuestionService.addQuestionToSurvey(id, questionDto);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, saved, "Question added to survey"));
    }

    @PostMapping("/{surveyId}/{questionId}/submitanswer")
    public ResponseEntity<ApiResponse<SurveyAnswerDto>> submitAnswer(@RequestBody SubmitSurveyAnswerRequest request, @PathVariable Long surveyId, @PathVariable Long questionId) {
        SurveyAnswerDto submitted = surveyAnswerService.submitAnswer(surveyId, questionId, request);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, submitted, "Answer submitted"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SurveyDetailsDto>> updateSurvey(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSurveyRequest request
    ) {
        SurveyDetailsDto updated = surveyService.updateSurvey(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, updated, "Survey updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteSurvey(@PathVariable Long id) {
        surveyService.softDeleteSurveyById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Survey soft-deleted"));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<Void>> hardDeleteSurvey(@PathVariable Long id) {
        surveyService.hardDeleteSurveyById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Survey hard-deleted"));
    }
}
