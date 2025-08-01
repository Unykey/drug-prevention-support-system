package com.swp08.dpss.repository.program;

import com.swp08.dpss.entity.program.ProgramParticipation;
import com.swp08.dpss.entity.program.ProgramParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramParticipationRepository extends JpaRepository<ProgramParticipation, ProgramParticipationId> {
    ProgramParticipation findByProgramIdAndUserId(Long programId, Long userId);
}
