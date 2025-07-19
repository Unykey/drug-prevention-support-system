package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.client.GuardianCreationRequest;
import com.swp08.dpss.dto.responses.GuardianResponse;
import com.swp08.dpss.entity.client.Guardian;
import com.swp08.dpss.entity.client.User;

import java.util.List;
import java.util.Optional;

public interface GuardianService {
    List<GuardianResponse> findAll();

    GuardianResponse addNewGuardian(GuardianCreationRequest guardianCreationRequest, String userEmail);

    GuardianResponse createNewGuardian(User user, GuardianCreationRequest guardianCreationRequest);

    Optional<Guardian> findByGuardianEmail(String email);

    Optional<Guardian> findByGuardianId(Long id);
}
