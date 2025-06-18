package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.ParentCreationRequest;
import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.entity.Parent;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.enums.User_Status;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.ParentService;
import com.swp08.dpss.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ParentService parentService;

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

        // Set the name, password, gender, date of birth, email, phone, (and parent) from the request
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

        // If a parent is provided, create a new ParentCreationRequest and add it to the list of parents for the new user
        if (request.getParent() != null) {
            Parent parentEntity;

            //Check if parent already exists in database
            Optional<Parent> existParent = parentService.findByParentEmail(request.getParent().getParentEmail());

            //If parent exists, find user based on thier parent, add parent to that user
            if (existParent.isPresent()) {
                System.out.println(existParent.get().getParentEmail());
                parentEntity = existParent.get();
            } else {
                //If parent does not exist, create new parent and add it to the user
                ParentCreationRequest newParent = new ParentCreationRequest();

                newParent.setParentName(request.getParent().getParentName());
                newParent.setParentEmail(request.getParent().getParentEmail());
                newParent.setParentPhone(request.getParent().getParentPhone());

                parentEntity = parentService.createNewParent(newParent);
            }

            newUser.addParent(parentEntity); // Add the parent to the list of parents for the new user
        } else {
            // If no parent is provided, set the list of parents to null
            newUser.setParents(null);
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
