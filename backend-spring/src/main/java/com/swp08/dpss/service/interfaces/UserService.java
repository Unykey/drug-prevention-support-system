package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.UpdateUserRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> findAll();

    Optional<User> findByEmail(String email);

    Optional<UserResponse> findUserById(Long id);

    Optional<UserResponse> findUserDetailById(Long id);

    boolean deleteById(Long id);

    // Guest Self-Registration
    User register(UserCreationRequest user);

    // Admin-Created Accounts
    User createNewUser(AdminUserCreationRequest user);

    // Password Reset
    String sendResetRequest(String email);

    List<UserResponse> searchUser(String name, String email);

    Optional<User> updateUserByAdmin(Long id, UpdateUserRequest request);

    Optional<User> updateOwnProfile(String email, UpdateUserRequest request);
}
