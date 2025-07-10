package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.AdminUserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.dto.requests.UserCreationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    // Guest Self-Registration
    User register(UserCreationRequest user);

    // Admin-Created Accounts
    User createNewUser(AdminUserCreationRequest user);

    // Password Reset
    String sendResetRequest(String email);
}
