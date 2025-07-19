package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.requests.survey.SubmitSurveyAnswerRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.survey.SurveyAnswerDto;
import com.swp08.dpss.service.interfaces.survey.SurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class SurveyAnswerController {

    private SurveyAnswerService surveyAnswerService;

    @Autowired
    public SurveyAnswerController(SurveyAnswerService surveyAnswerService) {
        this.surveyAnswerService = surveyAnswerService;
    }

    @PostMapping("/{surveyId}/{questionId}/submitanswer")
    public ResponseEntity<ApiResponse<SurveyAnswerDto>> submitAnswer(
            @RequestBody SubmitSurveyAnswerRequest request,
            @PathVariable Long surveyId,
            @PathVariable Long questionId,
            @AuthenticationPrincipal UserDetails userDetails) {
        SurveyAnswerDto submitted = surveyAnswerService.submitAnswer(surveyId, questionId, request, userDetails.getUsername());
        return ResponseEntity.status(201).body(new ApiResponse<>(true, submitted, "Answer submitted"));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> hardDeleteSurveyAnswer(@PathVariable Long id) {
        surveyAnswerService.hardDeleteSurveyAnswer(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Survey answer deleted successfully"));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> softDeleteSurveyAnswer(@PathVariable Long id) {
//        surveyAnswerService.softDeleteSurveyAnswer(id);
//        return ResponseEntity.noContent().build();
//    }


    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> submitMultipleAnswers(@RequestBody BulkSubmitSurveyAnswerRequest request) {
        surveyAnswerService.submitAllAnswers(request);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Multiple answers submitted successfully"));
    }
}
