package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.SurveyAnswerDto;
import com.swp08.dpss.dto.responses.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.service.interfaces.SurveyAnswerService;
import com.swp08.dpss.service.interfaces.SurveyQuestionService;
import com.swp08.dpss.service.interfaces.SurveyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyAnswerService surveyAnswerService;

    public SurveyController(
            SurveyService surveyService,
            SurveyQuestionService surveyQuestionService,
            SurveyAnswerService surveyAnswerService
    ) {
        this.surveyService = surveyService;
        this.surveyQuestionService = surveyQuestionService;
        this.surveyAnswerService = surveyAnswerService;
    }

    // ✅ Create a new survey
    @PostMapping
    public ResponseEntity<SurveyDetailsDto> createSurvey(
            @Valid @RequestBody CreateSurveyRequest request) {
        SurveyDetailsDto created = surveyService.createSurvey(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ✅ Get all surveys
    @GetMapping
    public ResponseEntity<List<SurveyDetailsDto>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    // ✅ Get a single survey by ID
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDetailsDto> getSurveyById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    // ✅ Get all questions for a survey
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<SurveyQuestionDto>> getQuestionsBySurveyId(@PathVariable Long id) {
        System.out.println("GET /api/surveys/" + id + "/questions called");

        return ResponseEntity.ok(surveyQuestionService.getQuestionsBySurveyId(id));
    }

    // ✅ Get all answers submitted to a survey
    @GetMapping("/{id}/answers")
    public ResponseEntity<List<SurveyAnswerDto>> getAnswersBySurveyId(@PathVariable Long id) {
        return ResponseEntity.ok(surveyAnswerService.getAnswersBySurveyId(id));
    }

    // ✅ Search surveys by name
    @GetMapping("/search")
    public ResponseEntity<List<SurveyDetailsDto>> searchSurveys(@RequestParam String keyword) {
        return ResponseEntity.ok(surveyService.searchSurveysByName(keyword));
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<SurveyQuestionDto> addQuestionToSurvey(
            @PathVariable Long surveyId,
            @RequestBody SurveyQuestionDto questionDto) {
        SurveyQuestionDto saved = surveyQuestionService.addQuestionToSurvey(surveyId, questionDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("questions/{questionId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteQuestionFromSurvey(@PathVariable Long questionId) {
        surveyQuestionService.deleteSurveyQuestionById(questionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("answer/{answerId}")
    public ResponseEntity<Void> deleteAnswerFromSurvey(@PathVariable Long answerId) {
        surveyAnswerService.deleteSurveyAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/submitanswer")
    public ResponseEntity<SurveyAnswerDto> submitAnswer(@RequestBody SubmitSurveyAnswerRequest request) {

        SurveyAnswerDto submitted = surveyAnswerService.submitAnswer(request);
        return new ResponseEntity<>(submitted, HttpStatus.CREATED);
    }
}
