package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.survey.UpdateSurveyQuestionRequest;
import com.swp08.dpss.dto.responses.survey.SurveyQuestionDto;
import com.swp08.dpss.service.interfaces.survey.SurveyQuestionService;
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
    public ResponseEntity<SurveyQuestionDto> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyQuestionService.getQuestionById(id));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteQuestionFromSurvey(@PathVariable Long id) {
        surveyQuestionService.softDeleteSurveyQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> hardDeleteQuestionFromSurvey(@PathVariable Long id) {
        surveyQuestionService.hardDeleteSurveyQuestionById(id);
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
