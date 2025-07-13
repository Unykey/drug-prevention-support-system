package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.UpdateUserRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.mapper.interfaces.UserMapper;
import com.swp08.dpss.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user") // Base path for all user-related operations
@EnableMethodSecurity
// Enable method-level security annotations to protect endpoints with Spring Security's @PreAuthorize annotation
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUser() {
        List<UserResponse> userResponseList = userService.findAll();
        return ResponseEntity.ok().body(new ApiResponse<>(true, userResponseList, "Get all users successfully"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'MANAGER')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUser(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        List<UserResponse> userResponseList = userService.searchUser(name, email);
        if (!userResponseList.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, userResponseList, "Search user successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user"));
        }
    }

    @GetMapping("/search/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'MANAGER')")
    public ResponseEntity<ApiResponse<UserResponse>> searchUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(user -> ResponseEntity.ok().body(new ApiResponse<>(true, user, "User Found")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user")));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserDetailById(@PathVariable Long id) {
        return userService.findUserDetailById(id)
                .map(user -> ResponseEntity.ok().body(new ApiResponse<>(true, user, "Get Full user info successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user")));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createNewUser(@Valid @RequestBody AdminUserCreationRequest user) {
        User newUser = userService.createNewUser(user);
        // Convert the new User to a UserResponse object and return it to the client
        UserResponse userResponse = userMapper.toResponse(newUser);
        return ResponseEntity.ok().body(new ApiResponse<>(true, userResponse, "Create new user successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(Authentication authentication) {
        // Get current user's email from Spring Security context
        String email = authentication.getName();

        // Load user details
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, userMapper.toResponse(user.get()), "Get user profile successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'MANAGER')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        boolean delete = userService.deleteById(id);
        if (delete) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, null, "Delete user successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserByAdmin(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        Optional<User> update = userService.updateUserByAdmin(id, request);
        if (update.isPresent()) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, userMapper.toResponse(update.get()), "Update user successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user"));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(@RequestBody UpdateUserRequest request, Authentication authentication) {
        Optional<User> update = userService.updateOwnProfile(authentication.getName(), request);
        if (update.isPresent()) {
            UserResponse response = userMapper.toResponse(update.get());
            return ResponseEntity.ok().body(new ApiResponse<>(true, response, "Update user successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user"));
    }
}
