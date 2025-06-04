package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User createNewUser(User user);
}
