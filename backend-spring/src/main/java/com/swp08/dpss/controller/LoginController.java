package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.LoginRequest;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.service.impls.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest) {
        User user = loginServiceImpl.login(loginRequest);
        if (user != null) {
            // For this simple demo, return only some user info
            // In a real app, you'd return a token, session ID, or more structured response.
            return ResponseEntity.ok("Login successful for user: " + user.getEmail() + " with role: " + user.getRole());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid email or password.");
        }
    }
}
