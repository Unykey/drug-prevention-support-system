package com.swp08.dpss.dto.requests.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantCreationRequest {
    private Long userId; // ID of the existing User entity

    private String bio;

    private String profilePicture;

    private Set<Long> specializationIds;

    private Set<Long> availabilityIds;

    private Set<Long> qualificationIds;
}
