package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.GuardianCreationRequest;
import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.entity.Guardian;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.enums.User_Status;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.GuardianService;
import com.swp08.dpss.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final GuardianService guardianService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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
    @Override
    public User register(UserCreationRequest request) {
        User newUser = new User();

        // Set the name, password, gender, date of birth, email, phone, (and Guardian) from the request
        newUser.setName(request.getName());

        // Encode the password before saving it to the database
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // If the gender is not provided, set it to PREFER_NOT_TO_SAY
        if (request.getGender() == null) {
            newUser.setGender(Genders.PREFER_NOT_TO_SAY);
        } else {
            newUser.setGender(request.getGender());
        }

        newUser.setDateOfBirth(request.getDateOfBirth());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());

        // If a Guardian is provided, create a new GuardianCreationRequest and add it to the list of Guardians for the new user
        if (request.getGuardian() != null) {
            Guardian guardianEntity;

            //Check if Guardian already exists in database
            Optional<Guardian> existGuardian = guardianService.findByGuardianEmail(request.getGuardian().getGuardianEmail());

            //If Guardian exists, find user based on thier Guardian, add Guardians to that user
            if (existGuardian.isPresent()) {
                System.out.println(existGuardian.get().getGuardianEmail());
                guardianEntity = existGuardian.get();
            } else {
                //If Guardian does not exist, create new Guardian and add it to the user
                GuardianCreationRequest newGuardian = new GuardianCreationRequest();

                newGuardian.setGuardianName(request.getGuardian().getGuardianName());
                newGuardian.setGuardianEmail(request.getGuardian().getGuardianEmail());
                newGuardian.setGuardianPhone(request.getGuardian().getGuardianPhone());

                guardianEntity = guardianService.createNewGuardian(newGuardian);
            }

            newUser.addGuardian(guardianEntity); // Add the Guardian to the list of Guardians for the new user
        } else {
            // If no Guardian is provided, set the list of Guardians to null
            newUser.setGuardianList(null);
        }

        newUser.setRole(Roles.MEMBER);

        newUser.setStatus(User_Status.PENDING);

        return userRepository.save(newUser);
    }

    // Admin-Created Accounts
    @Override
    public User createNewUser(User request) {
        User newUser = new User();

        newUser.setName(request.getName());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (request.getGender() == null) {
            newUser.setGender(Genders.PREFER_NOT_TO_SAY);
        } else {
            newUser.setGender(request.getGender());
        }

        newUser.setDateOfBirth(request.getDateOfBirth());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());

        newUser.setRole(request.getRole());
        return userRepository.save(newUser);
    }
}
