package com.swp08.dpss.dto.responses.consultant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QualificationResponse {
    private final Long qualificationId;
    private final String name;
    private final String institution;
    private final Integer yearObtained;
    private final String certificationNumber;
    private final String issuingAuthority;
    private final String description;
}
