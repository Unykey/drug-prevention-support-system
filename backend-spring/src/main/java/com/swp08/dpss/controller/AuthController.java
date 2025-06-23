package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.AuthRequest;
import com.swp08.dpss.dto.requests.LoginRequest;
import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.dto.responses.AuthResponse;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.security.jwt.JwtUtil;
import com.swp08.dpss.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        System.out.println("=".repeat(20));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password. " + request.getEmail() + " " + request.getPassword() + " " + e.getMessage());
        }

        // Create a new User object with the email and password from the request
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreationRequest user) {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        userService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
