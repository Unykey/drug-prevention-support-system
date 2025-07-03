package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.Guardian;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.mapper.interfaces.UserMapper;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.GuardianService;
import com.swp08.dpss.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final GuardianService guardianService;

    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        //Get all users from database
        List<User> userList = userRepository.findAll();

        //Convert each user to a UserResponse object and add it to a list
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getGender(),
                    user.getDateOfBirth(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getGuardians().stream().map(Guardian::getGuardianId).toList() // Convert Guardian list to a list of Guardian IDs
            );

            userResponseList.add(userResponse);
        }
        return userResponseList;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // Guest Self-Registration
    @Transactional
    @Override
    public User register(UserCreationRequest request) {
        // Create a New User and map the request to the new User's fields'
        User newUser = userMapper.toEntity(request, passwordEncoder);

        // Save the new user to the database before creating a Guardian for it
        userRepository.save(newUser);

        // If a Guardian is provided, create a new GuardianCreationRequest and add it to the list of Guardians for the new user
        if (request.getGuardian() != null) {
            guardianService.createNewGuardian(newUser, request.getGuardian());
        }
        return newUser;
    }

    // Admin-Created Accounts
    @Override
    public User createNewUser(AdminUserCreationRequest request) {
        // Create a New User and map the request to the new User's fields'
        User newUser = userMapper.toEntity(request, passwordEncoder);

        // Save the new user to the database
        return userRepository.save(newUser);
    }
}
