package com.swp08.dpss.dto.requests.program;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramParticipationRequest {
    private Long programId;
    private Long userId;
}
