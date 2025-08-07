package com.swp08.dpss.dto.responses.consultant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ConsultantResponse {
    private final Long consultantId;
    private final String bio;
    private final String profilePicture;
    private final Set<SpecializationResponse> specializations;
    private final Set<AvailabilityResponse> availabilities;
    private final Set<QualificationResponse> qualifications;
}
