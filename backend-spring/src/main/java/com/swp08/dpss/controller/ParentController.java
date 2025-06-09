package com.swp08.dpss.controller;

import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.service.interfaces.ParentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController {
    @Autowired
    private ParentService parentService;

    @GetMapping
    public ResponseEntity getParent() {
        List<Parent> parents = parentService.findAll();
        return ResponseEntity.ok().body(parents);
    }

    @PostMapping
    public ResponseEntity createNewParent(@Valid @RequestBody Parent parent) {
        Parent newParent = new Parent();
        return ResponseEntity.ok().body(newParent);
    }
}
