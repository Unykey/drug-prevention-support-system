package com.swp08.dpss.controller;

import com.swp08.dpss.service.interfaces.consultant.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultant")
@RequiredArgsConstructor
public class ConsultantController {
    private final ConsultantService consultantService;
}
