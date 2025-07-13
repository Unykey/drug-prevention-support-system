package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.AuthRequest;
import com.swp08.dpss.dto.requests.client.ForgotPasswordRequest;
import com.swp08.dpss.dto.requests.client.ResetPasswordRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.AuthResponse;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.mapper.interfaces.UserMapper;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.security.jwt.JwtUtil;
import com.swp08.dpss.service.interfaces.TokenService;
import com.swp08.dpss.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Invalid email or password."));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: "));

        UserResponse userResponse = userMapper.toResponse(user);
        AuthResponse authResponse = new AuthResponse(jwt, userResponse);
        return ResponseEntity.ok(new ApiResponse<>(true, authResponse, "Login successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreationRequest user) {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Email already in use"));
        }

        if (userRepository.existsUserByPhone(user.getPhone())) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Phone number already in use"));
        }

        userService.register(user);
        return ResponseEntity.ok(new ApiResponse<>(true, user, "User registered successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        // TODO: Find by Phone number
        // Email/Phone number of Guardian if User is a child (<18 years old)
        if (userRepository.existsUserByEmail(request.getEmail())) {
            //userService.sendResetRequest(request.getEmail());
        }

        return ResponseEntity.ok(new ApiResponse<>(true, null, "If the email is registered, a reset link has been sent"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        // Check if password and confirmation match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Password and confirmation do not match!"));
        }

        boolean success = tokenService.resetPassword(request.getToken(), request.getPassword());
        if (success) {
            return ResponseEntity.ok(new ApiResponse<>(true, null, "Password has been reset successfully!"));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Reset token is invalid or expired!"));
        }
    }
}
