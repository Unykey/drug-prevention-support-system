package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.GuardianCreationRequest;
import com.swp08.dpss.entity.Guardian;
import com.swp08.dpss.repository.GuardianRepository;
import com.swp08.dpss.service.interfaces.GuardianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianServiceImpl implements GuardianService {
    @Autowired
    GuardianRepository guardianRepository;

    @Override
    public List<Guardian> findAll() {
        return guardianRepository.findAll();
    }

    @Override
    public Guardian createNewGuardian(GuardianCreationRequest guardian) {
        Guardian newGuardian = new Guardian();
        newGuardian.setGuardianName(guardian.getGuardianName());
        newGuardian.setGuardianEmail(guardian.getGuardianEmail());
        newGuardian.setGuardianPhone(guardian.getGuardianPhone());
        return guardianRepository.save(newGuardian);
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
