package com.swp08.dpss.controller;

import com.swp08.dpss.entity.Guardian;
import com.swp08.dpss.service.interfaces.GuardianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createNewGuardian(@Valid @RequestBody Guardian guardian) {
        Guardian newGuardian1 = new Guardian();
        return ResponseEntity.ok().body(newGuardian1);
    }
}
