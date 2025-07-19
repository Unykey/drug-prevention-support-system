package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.survey.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import jakarta.validation.Valid; // Added for @Valid annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Keep if PreAuthorize is used
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List; // For get answers by user/question if you add them

@RestController
@RequestMapping("/api/answers")
public class SurveyAnswerController {

    private final SurveyAnswerService surveyAnswerService; // Made final

    @Autowired
    public SurveyAnswerController(SurveyAnswerService surveyAnswerService) {
        this.surveyAnswerService = surveyAnswerService;
    }

    @PostMapping("/{surveyId}/questions/{questionId}/submit") // Clarified endpoint path
    public ResponseEntity<ApiResponse<SurveyAnswerDto>> submitAnswer(
            @PathVariable Long surveyId,
            @PathVariable Long questionId,
            @Valid @RequestBody SubmitSurveyAnswerRequest request, // Added @Valid
            @AuthenticationPrincipal UserDetails userDetails) {
        SurveyAnswerDto submitted = surveyAnswerService.submitAnswer(surveyId, questionId, request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, submitted, "Answer submitted successfully"));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER', 'ADMIN', 'MEMBER') and #userId == authentication.principal.id") // Example: User can see their own answers
    public ResponseEntity<ApiResponse<List<SurveyAnswerDto>>> getAnswersByUserId(@PathVariable Long userId) {
        List<SurveyAnswerDto> answers = surveyAnswerService.getAnswersByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, answers, "Answers for user retrieved"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER', 'ADMIN')") // Access control as needed
    public ResponseEntity<ApiResponse<SurveyAnswerDto>> getAnswerById(@PathVariable Long id) {
        SurveyAnswerDto answer = surveyAnswerService.getAnswerById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, answer, "Answer retrieved"));
    }

    // Assuming this hard delete is for individual answer records
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSurveyAnswer(@PathVariable Long id) { // Renamed from hardDeleteSurveyAnswer
        surveyAnswerService.deleteAnswer(id); // Changed from hardDeleteSurveyAnswer
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Survey answer deleted successfully"));
    }


    // Bulk submission - ensure BulkSubmitSurveyAnswerRequest matches service expectation
    @PostMapping("/bulk/{surveyId}/submit") // Added surveyId to bulk endpoint for context
    public ResponseEntity<ApiResponse<Void>> submitMultipleAnswers(
            @PathVariable Long surveyId, // Pass surveyId for context
            @Valid @RequestBody BulkSubmitSurveyAnswerRequest request, // Added @Valid
            @AuthenticationPrincipal UserDetails userDetails) {
        // Assuming your service method is `submitAllAnswers(Long surveyId, BulkSubmitSurveyAnswerRequest request, String userEmail)`
        surveyAnswerService.submitAllAnswers(surveyId, request, userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Multiple answers submitted successfully"));
    }
}