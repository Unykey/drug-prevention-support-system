package com.swp08.dpss.controller;

import com.swp08.dpss.entity.User;
import com.swp08.dpss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAPI {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {
        return "index"; // This maps to src/main/resources/templates/index.html (Thymeleaf)
    }

    @GetMapping("/api/user")
    public ResponseEntity getUser() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/api/user")
    public ResponseEntity createNewUser(User user) {
        User newUser = userService.createNewUser(user);
        return ResponseEntity.ok().body(newUser);
    }


}
