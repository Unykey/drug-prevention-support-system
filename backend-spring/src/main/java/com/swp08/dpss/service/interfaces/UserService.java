package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.entity.User;
import com.swp08.dpss.dto.requests.UserCreationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    // Guest Self-Registration
    User register(UserCreationRequest user);

    // Admin-Created Accounts
    User createNewUser(User user);

}
