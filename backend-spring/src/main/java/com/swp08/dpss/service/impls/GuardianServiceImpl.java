package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.GuardianCreationRequest;
import com.swp08.dpss.dto.responses.GuardianResponse;
import com.swp08.dpss.entity.client.Guardian;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.repository.GuardianRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {
    private final GuardianRepository guardianRepository;
    private final UserRepository userRepository;

    @Override
    public List<Guardian> findAll() {
        return guardianRepository.findAll();
    }

    //Add Guardian for existing User
    @Override
    public GuardianResponse addNewGuardian(GuardianCreationRequest guardianCreationRequest, String userEmail) {
        //Find user by email (from JWT token)
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userEmail));

        //Validate guardian email uniquiness
        guardianRepository.findByGuardianEmail(guardianCreationRequest.getGuardianEmail()).ifPresent(guardian -> {
            throw new IllegalArgumentException("Guardian email: " + guardian.getGuardianEmail() + " is already in use");
        });

        //Create new guardian
        Guardian newGuardian = new Guardian();
        newGuardian.setGuardianName(guardianCreationRequest.getGuardianName());
        newGuardian.setGuardianEmail(guardianCreationRequest.getGuardianEmail());
        newGuardian.setGuardianPhone(guardianCreationRequest.getGuardianPhone());

        //Link user to guardian
        user.addGuardian(newGuardian);

        //Save new guardian and user
        Guardian save = guardianRepository.save(newGuardian);
        return new GuardianResponse(
                save.getGuardianId(),
                save.getGuardianName(),
                save.getGuardianEmail(),
                save.getGuardianPhone()
        );
    }

    //Create new Guardian while creating User
    @Override
    public GuardianResponse createNewGuardian(User user, GuardianCreationRequest guardianCreationRequest) {
        //Check for existing guardian by email
        Optional<Guardian> existingGuardian = guardianRepository.findByGuardianEmail(guardianCreationRequest.getGuardianEmail());

        //If guardian exists, check if name and phone are the same
        if (existingGuardian.isPresent()) {
            //Verify guardian phone and name
            if (existingGuardian.get().getGuardianPhone().equals(guardianCreationRequest.getGuardianPhone())
                    && existingGuardian.get().getGuardianName().equals(guardianCreationRequest.getGuardianName())) {
                //If guardian already exists, add exist Guardian to User and return response
                user.addGuardian(existingGuardian.get());
                return new GuardianResponse(
                        existingGuardian.get().getGuardianId(),
                        existingGuardian.get().getGuardianName(),
                        existingGuardian.get().getGuardianEmail(),
                        existingGuardian.get().getGuardianPhone()
                );
            } else {
                //If guardian already exists, but phone or name is different, throw exception
                throw new IllegalArgumentException("Guardian email: " + guardianCreationRequest.getGuardianEmail() + " exist but name or phone number differs. Check again details and try again. If problem persists, contact support team. Thank you for your cooperation.");
            }
        }

        //If guardian does not exist, create new Guardian, add new Guardian to user and return response
        Guardian newGuardian = new Guardian();
        newGuardian.setGuardianName(guardianCreationRequest.getGuardianName());
        newGuardian.setGuardianEmail(guardianCreationRequest.getGuardianEmail());
        newGuardian.setGuardianPhone(guardianCreationRequest.getGuardianPhone());

        //Link user to guardian
        user.addGuardian(newGuardian);

        //Save new guardian and user
        Guardian save = guardianRepository.save(newGuardian);
        return new GuardianResponse(
                save.getGuardianId(),
                save.getGuardianName(),
                save.getGuardianEmail(),
                save.getGuardianPhone()
        );
    }

    @Override
    public Optional<Guardian> findByGuardianEmail(String email) {
        return guardianRepository.findByGuardianEmail(email);
    }

    @Override
    public Optional<Guardian> findByGuardianId(Long id) {
        return guardianRepository.findByGuardianId(id);
    }
}
