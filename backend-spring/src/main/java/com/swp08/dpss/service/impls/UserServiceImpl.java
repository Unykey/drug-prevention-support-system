package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.UpdateUserRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.enums.User_Status;
import com.swp08.dpss.mapper.interfaces.UserMapper;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.GuardianService;
import com.swp08.dpss.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        // Convert User list to a list of UserResponses using the UserMapper class
        List<UserResponse> userResponseList = userMapper.toUserResponseList(userList);
        return userResponseList;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserResponse> findUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toResponse);
    }

    @Override
    public Optional<UserResponse> findUserDetailById(Long id) {
        return userRepository.findById(id).map(userMapper::toResponse);
    }

    @Override
    public boolean deleteById(Long id) {
        // Find user by id
        Optional<User> userOptional = userRepository.findById(id);
        System.out.println();
        // Change status of user to inactive
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(User_Status.DELETED);
            userRepository.save(user);
            return true;
        }
        return false;
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

    @Override
    public String sendResetRequest(String email) {
        String token = UUID.randomUUID().toString(); // Generate a random token for the user's password reset request

        //save token in password_reset_token that have: user_id, token, expiryTime
        //TODO: userRepository.savePasswordResetToken(email, token, LocalDateTime.now().plusMinutes(15)); // Save the token and expiryTime in the database using the UserRepository.savePasswordResetToken() method.
        //Test code:
        System.out.println("email: " + email);
        System.out.println("token: " + token);

        // send email (pseudocode)
        String resetLink = "http://localhost:3030/reset-password?token=" + token; // Change actual frontend domain
        //TODO: send email to user with reset link
        //emailService.sendEmail(email, "Reset Password", resetLink);
        return resetLink; // Return the reset link to the client. This link will be used to reset the user's password.
    }

    @Override
    public List<UserResponse> searchUser(String name, String email) {
        //Find users by name or email or both
        List<User> userList;

        if (name != null && email != null) {
            userList = userRepository.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(name, email);
        } else if (name != null) {
            userList = userRepository.findByNameContainingIgnoreCase(name);
        } else if (email != null) {
            userList = userRepository.findByEmailContainingIgnoreCase(email);
        } else {
            //If no search parameters are provided, return all users
            userList = userRepository.findAll();
        }
        return userMapper.toUserResponseList(userList);
    }

    @Override
    public Optional<User> updateUserByAdmin(Long id, UpdateUserRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            applyUpdate(user, request, true);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateOwnProfile(String email, UpdateUserRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            applyUpdate(user, request, false);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    private void applyUpdate(User user, UpdateUserRequest request, boolean isAdmin) {
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        if (isAdmin) {
            if (request.getRole() != null) {
                user.setRole(request.getRole());
            }
            if (request.getStatus() != null) {
                user.setStatus(request.getStatus());
            }
        }
    }
}
