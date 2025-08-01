package com.swp08.dpss.dto.responses.program;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramParticipationResponse {
    private Long userId;
    private Long programId;
    private LocalDateTime joinedAt;
    // You might add user details here if needed, e.g., private String userName;
}
