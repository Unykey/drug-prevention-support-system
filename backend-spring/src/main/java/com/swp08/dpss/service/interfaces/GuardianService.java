package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.GuardianCreationRequest;
import com.swp08.dpss.entity.Guardian;

import java.util.List;
import java.util.Optional;

public interface GuardianService {
    List<Guardian> findAll();

    Guardian createNewGuardian(GuardianCreationRequest guardianCreationRequest);

    Optional<Guardian> findByGuardianEmail(String email);

    Optional<Guardian> findByGuardianId(Long id);
}
