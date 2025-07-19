package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.survey.SurveyQuestionRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
import jakarta.validation.Valid; // Added for @Valid annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class SurveyQuestionController {
    private final SurveyQuestionService surveyQuestionService;

    @Autowired
    public SurveyQuestionController(SurveyQuestionService surveyQuestionService) {
        this.surveyQuestionService = surveyQuestionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SurveyQuestionDto>> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, surveyQuestionService.getQuestionById(id), "Question retrieved"));
    }

    // Aligned with service method name `deleteQuestion`
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
        // Renamed method to match service interface
        surveyQuestionService.deleteQuestion(id); // Changed from hardDeleteSurveyQuestionById
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Question deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SurveyQuestionDto>> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody SurveyQuestionRequest request) { // Added @Valid
        SurveyQuestionDto updated = surveyQuestionService.updateQuestion(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, updated, "Question updated"));
    }
}