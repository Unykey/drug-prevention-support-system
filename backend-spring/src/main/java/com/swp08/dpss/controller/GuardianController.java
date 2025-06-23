package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.GuardianCreationRequest;
import com.swp08.dpss.dto.responses.GuardianResponse;
import com.swp08.dpss.entity.Guardian;
import com.swp08.dpss.service.interfaces.GuardianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guardian")
@RequiredArgsConstructor
public class GuardianController {
    private final GuardianService guardianService;

    @GetMapping
    public ResponseEntity getGuardian() {
        List<Guardian> guardianList = guardianService.findAll();
        return ResponseEntity.ok().body(guardianList);
    }

    @PostMapping
    public ResponseEntity<GuardianResponse> createNewGuardian(@Valid @RequestBody GuardianCreationRequest guardian, Authentication authentication) {
        String email = authentication.getName();
        GuardianResponse response = guardianService.addNewGuardian(guardian, email);
        return ResponseEntity.ok().body(response);
    }

//    @PreAuthorize("hasRole('ADMIN')")
}
