package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.AdminUserCreationRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user") // Base path for all user-related operations
@EnableMethodSecurity // Enable method-level security annotations to protect endpoints with Spring Security's @PreAuthorize annotation
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
    public ResponseEntity<ApiResponse<UserResponse>> searchUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(user -> ResponseEntity.ok().body(new ApiResponse<>(true, user, "User Found")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user")))
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<User>> getUserDetailById(@PathVariable Long id) {
        return userService.findUserDetailById(id)
                .map(user -> ResponseEntity.ok().body(new ApiResponse<>(true, user, "Get Full user info successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, null, "Not found user")));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody AdminUserCreationRequest user) {
        User newUser = userService.createNewUser(user);
        // Convert the new User to a UserResponse object and return it to the client
        UserResponse userResponse = userMapper.toResponse(newUser);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
