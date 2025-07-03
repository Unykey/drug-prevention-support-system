package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.mapper.interfaces.UserMapper;
import com.swp08.dpss.service.impls.UserServiceImpl;
import com.swp08.dpss.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user") // Base path for all user-related operations
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody User user) {
        User newUser = userService.createNewUser(user);
        return ResponseEntity.ok().body(userMapper.userToUserResponse(newUser));
    }

    // Endpoint to get a user by email
    // Example: GET /api/users/search?email=test@example.com
    @GetMapping("/search")
    public ResponseEntity getUserByEmail(@RequestParam String email) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }

        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User with Email: " + email);
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity getUserByPhone(@RequestParam Long id) {
        Optional<User> findUser = userService.findById(id);
        return ResponseEntity.ok().body(findUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
