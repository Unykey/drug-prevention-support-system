package com.swp08.dpss.repository.consultant;

import com.swp08.dpss.entity.consultant.Qualification;
import com.swp08.dpss.entity.consultant.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByName(String name);
}
