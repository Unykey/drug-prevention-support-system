package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.service.interfaces.SurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class SurveyAnswerController {

    private SurveyAnswerService surveyAnswerService;

    @Autowired
    public SurveyAnswerController(SurveyAnswerService surveyAnswerService) {
        this.surveyAnswerService = surveyAnswerService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> hardDeleteSurveyAnswer(@PathVariable Long id) {
        surveyAnswerService.hardDeleteSurveyAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteSurveyAnswer(@PathVariable Long id) {
        surveyAnswerService.softDeleteSurveyAnswer(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/bulk")
    public ResponseEntity<Void> submitMultipleAnswers(@RequestBody BulkSubmitSurveyAnswerRequest request) {
        surveyAnswerService.submitAllAnswers(request);
        return ResponseEntity.ok().build();
    }
}
