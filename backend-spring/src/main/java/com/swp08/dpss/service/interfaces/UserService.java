package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User createNewUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void deleteById(Long id);

}
