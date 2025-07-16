package com.swp08.dpss.entity.program;

import com.swp08.dpss.entity.client.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProgramParticipation {
    @EmbeddedId
    private ProgramParticipationId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("programId")
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();
}
