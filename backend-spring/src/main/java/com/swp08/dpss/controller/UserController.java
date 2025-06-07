package com.swp08.dpss.controller;

import com.swp08.dpss.entity.User;
import com.swp08.dpss.service.impls.UserServiceImpl;
import com.swp08.dpss.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user") // Base path for all user-related operations
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/api/user")
    @GetMapping
    public ResponseEntity getUser() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

//    @PostMapping("/api/user")
    @PostMapping
    public ResponseEntity createNewUser(@Valid @RequestBody User user) {
        User newUser = userService.createNewUser(user);
        return ResponseEntity.ok().body(newUser);
    }

//    @PutMapping
//    public ResponseEntity updateUser(@Valid @RequestBody User user) {
//        User updateUser = userService.updateUser(user);
//        return ResponseEntity.ok().body(updateUser);
//    }

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
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User with email: " + email);
        }
    }

//    @DeleteMapping
//    public ResponseEntity deleteUser(@Valid @RequestBody User user) {
//
//    }
}
