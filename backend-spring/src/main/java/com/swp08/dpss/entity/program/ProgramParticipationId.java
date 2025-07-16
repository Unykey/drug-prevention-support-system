package com.swp08.dpss.entity.program;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProgramParticipationId implements Serializable {
    private Long userId;
    private Long programId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramParticipationId that)) return false;
        return userId.equals(that.userId) && programId.equals(that.programId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + programId.hashCode();
    }
}
