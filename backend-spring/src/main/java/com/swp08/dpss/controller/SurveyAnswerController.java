package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.BulkSubmitSurveyAnswerRequest;
import com.swp08.dpss.service.interfaces.SurveyAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answers")
public class SurveyAnswerController {
    private SurveyAnswerService surveyAnswerService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        surveyAnswerService.deleteSurveyAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> submitMultipleAnswers(@RequestBody BulkSubmitSurveyAnswerRequest request) {
        surveyAnswerService.submitAllAnswers(request);
        return ResponseEntity.ok().build();
    }
}
