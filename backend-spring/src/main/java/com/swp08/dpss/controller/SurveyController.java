package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.survey.AddSurveyQuestionRequest;
import com.swp08.dpss.dto.requests.survey.CreateSurveyRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.UpdateSurveyRequest;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.dto.responses.survey.SurveyDetailsDto;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.enums.SurveyStatus;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import com.swp08.dpss.service.interfaces.survey.SurveyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // ✅ Create a new survey
    // ROLE: MANAGER, ADMIN
    @PostMapping
    public ResponseEntity<SurveyDetailsDto> createSurvey(
            @Valid @RequestBody CreateSurveyRequest request) {
        SurveyDetailsDto created = surveyService.createSurvey(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ✅ Get published surveys (default for MEMBER)
    // ROLE: MEMBER
    @GetMapping
    public ResponseEntity<List<SurveyDetailsDto>> getPublishedSurveys() {
        return ResponseEntity.ok(surveyService.getSurveysByStatus(SurveyStatus.PUBLISHED));
    }

    // ✅ Get all surveys with optional status param
    // ROLE: ADMIN, MANAGER, STAFF
    @GetMapping("/all")
    public ResponseEntity<List<SurveyDetailsDto>> getAllSurveys(
            @RequestParam(required = false) SurveyStatus status,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (status != null) {
            return ResponseEntity.ok(surveyService.getSurveysByStatus(status));
        }
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    // ✅ Get a single survey by ID
    // ROLE: ALL ROLES, depending on visibility logic
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDetailsDto> getSurveyById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    // ✅ Get questions for a survey
    // ROLE: ALL ROLES (visible filtered by role in service logic)
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<SurveyQuestionDto>> getQuestionsBySurveyId(@PathVariable Long id) {
        return ResponseEntity.ok(surveyQuestionService.getQuestionsBySurveyId(id));
    }

    // ✅ Get answers submitted to a survey
    // ROLE: MEMBER (own answers), STAFF, MANAGER, ADMIN
    @GetMapping("/{id}/answers")
    public ResponseEntity<List<SurveyAnswerDto>> getAnswersBySurveyId(@PathVariable Long id) {
        return ResponseEntity.ok(surveyAnswerService.getAnswersBySurveyId(id));
    }

    // ✅ Search surveys by name
    // ROLE: ALL
    @GetMapping("/search")
    public ResponseEntity<List<SurveyDetailsDto>> searchSurveys(@RequestParam String keyword) {
        return ResponseEntity.ok(surveyService.searchSurveysByName(keyword));
    }

    // ✅ Add question to survey
    // ROLE: MANAGER, ADMIN
    @PostMapping("/{id}/addquestion")
    public ResponseEntity<SurveyQuestionDto> addQuestionToSurvey(
            @RequestBody AddSurveyQuestionRequest questionDto, @PathVariable Long id) {
        SurveyQuestionDto saved = surveyQuestionService.addQuestionToSurvey(id, questionDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ✅ Submit answer
    // ROLE: MEMBER
    @PostMapping("/{surveyId}/{questionId}/submitanswer")
    public ResponseEntity<SurveyAnswerDto> submitAnswer(@RequestBody SubmitSurveyAnswerRequest request, @PathVariable Long surveyId, @PathVariable Long questionId) {
        SurveyAnswerDto submitted = surveyAnswerService.submitAnswer(surveyId, questionId, request);
        return new ResponseEntity<>(submitted, HttpStatus.CREATED);
    }

    // ✅ Update survey
    // ROLE: MANAGER, ADMIN
    // ✅ Update an existing survey (including status)
    // ROLE: MANAGER, ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<SurveyDetailsDto> updateSurvey(
            @PathVariable Long id,
            @Valid@RequestBody UpdateSurveyRequest request // or UpdateSurveyRequest if separate
    ) {
        SurveyDetailsDto updated = surveyService.updateSurvey(id, request);
        return ResponseEntity.ok(updated);
    }



    // ✅ Soft delete a survey
    // ROLE: MANAGER, ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteSurvey(@PathVariable Long id) {
        surveyService.softDeleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Hard delete a survey
    // ROLE: ADMIN
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> hardDeleteSurvey(@PathVariable Long id) {
        surveyService.hardDeleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }

}
