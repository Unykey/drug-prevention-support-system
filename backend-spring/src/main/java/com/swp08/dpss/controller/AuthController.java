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
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Sai email hoặc mật khẩu. Vui lòng kiểm tra và thử lại."));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email này"));

        UserResponse userResponse = userMapper.toResponse(user);
        AuthResponse authResponse = new AuthResponse(jwt, userResponse);
        return ResponseEntity.ok(new ApiResponse<>(true, authResponse, "Đăng nhập thành công!"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreationRequest user) {
        if (userRepository.existsUserByEmail(user.getEmail())) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Email này đã được đăng ký! Vui lòng sử dụng email khác."));
        }

        if (userRepository.existsUserByPhone(user.getPhone())) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Số điện thoại này đã được đăng ký! Vui lòng sử dụng số điện thoại khác."));
        }

        userService.register(user);
        return ResponseEntity.ok(new ApiResponse<>(true, user, "Đăng ký thành công!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        // TODO: Find by Phone number
        // Email/Phone number of Guardian if User is a child (<18 years old)
        if (userRepository.existsUserByEmail(request.getEmail())) {
            //userService.sendResetRequest(request.getEmail());
        }

        return ResponseEntity.ok(new ApiResponse<>(true, null, "Nếu như email của bạn đã được đăng ký, một đường link đặt lại mật khẩu sẽ được gửi."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        // Check if password and confirmation match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Mật khẩu và xác nhận mật khẩu không trùng khớp. Vui lòng thử lại."));
        }

        boolean success = tokenService.resetPassword(request.getToken(), request.getPassword());
        if (success) {
            return ResponseEntity.ok(new ApiResponse<>(true, null, "Mật khẩu đã được đặt lại thành công!"));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Token để đặt lại mật khẩu không hợp lệ hoặc đã hết hạn. Vui lòng thử lại."));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract JWT from Authorization header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(new ApiResponse<>(false, null, "Token không hợp lệ"));
            }
            String jwt = authHeader.replace("Bearer ", "");

            // Validate token and extract username
            String username = jwtUtil.extractUsername(jwt);
            if (jwtUtil.isTokenExpired(jwt)) {
                return ResponseEntity.status(401).body(new ApiResponse<>(false, null, "Token đã hết hạn"));
            }

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!jwtUtil.validateToken(jwt, userDetails)) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, null, "Token không hợp lệ"));
            }

            // Generate new token
            String newJwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new ApiResponse<>(true, newJwt, "Token refreshed thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, null, "Token refresh thất bại: " + e.getMessage()));
        }
    }
}
