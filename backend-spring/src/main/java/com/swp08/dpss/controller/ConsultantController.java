package com.swp08.dpss.controller;

import com.swp08.dpss.service.interfaces.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/consultant")
@RequiredArgsConstructor
public class ConsultantController {
    private final ConsultantService consultantService;
}
