package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.client.GuardianCreationRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.GuardianResponse;
import com.swp08.dpss.entity.client.Guardian;
import com.swp08.dpss.service.interfaces.GuardianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guardian")
@RequiredArgsConstructor
public class GuardianController {
    private final GuardianService guardianService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GuardianResponse>>> getGuardian() {
        List<GuardianResponse> guardianList = guardianService.findAll();
        return ResponseEntity.ok().body(new ApiResponse<>(true, guardianList, "Get all guardians successfully"));
    }

    @PostMapping
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GuardianResponse> createNewGuardian(@Valid @RequestBody GuardianCreationRequest guardian, Authentication authentication) {
        String email = authentication.getName();
        GuardianResponse response = guardianService.addNewGuardian(guardian, email);
        return ResponseEntity.ok().body(response);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity createNewGuardian(@Valid @RequestBody GuardianCreationRequest guardian) {
//        Guardian newGuardian1 = new Guardian();
//        return ResponseEntity.ok().body(newGuardian1);
//    }
}
