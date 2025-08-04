package com.swp08.dpss.controller;

import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.entity.consultant.Consultant;
import com.swp08.dpss.service.interfaces.consultant.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultant")
@RequiredArgsConstructor
public class ConsultantController {
    private final ConsultantService consultantService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Consultant>>> getConsultant() {
        List<Consultant> consultantList = consultantService.findAll();
        return ResponseEntity.ok().body(new ApiResponse<>(true, consultantList, "Get all consultants successfully"));
    }
}
