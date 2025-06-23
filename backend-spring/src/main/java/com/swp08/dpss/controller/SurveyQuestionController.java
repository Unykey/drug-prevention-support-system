package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.SurveyQuestionDto;
import com.swp08.dpss.service.interfaces.SurveyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class SurveyQuestionController {
    private final SurveyQuestionService surveyQuestionService;

    @GetMapping("/{id}")
    public ResponseEntity<SurveyQuestionDto> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyQuestionService.getQuestionById(id));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteQuestionFromSurvey(@PathVariable Long questionId) {
        surveyQuestionService.deleteSurveyQuestionById(questionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyQuestionDto> updateQuestion(
            @PathVariable Long id,
            @RequestBody UpdateSurveyQuestionRequest request) {
        SurveyQuestionDto updated = surveyQuestionService.updateQuestion(id, request);
        return ResponseEntity.ok(updated);
    }
}
