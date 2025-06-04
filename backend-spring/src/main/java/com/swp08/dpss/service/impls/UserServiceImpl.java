package com.swp08.dpss.service.impls;

import com.swp08.dpss.entity.User;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createNewUser(User user) {
        return userRepository.save(user);
    }
}
