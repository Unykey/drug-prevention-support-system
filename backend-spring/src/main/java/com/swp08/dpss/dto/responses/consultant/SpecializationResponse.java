package com.swp08.dpss.dto.responses.consultant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SpecializationResponse {
    private final Long specializationId;
    private final String name;
    private final String description;
}
