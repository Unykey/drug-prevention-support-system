package com.swp08.dpss.repository;

import com.swp08.dpss.entity.client.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian,Long> {
    Optional<Guardian> findByGuardianEmail(String email);

    Optional<Guardian> findByGuardianId(Long id);
}
