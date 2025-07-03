package com.swp08.dpss.dto.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class GuardianResponse {
    private final Long guardianId;
    private final String guardianName;
    private final String guardianEmail;
    private final String guardianPhone;
}
