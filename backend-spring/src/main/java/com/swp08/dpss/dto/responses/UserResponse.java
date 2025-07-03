package com.swp08.dpss.dto.responses;

import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class UserResponse {
    private final Long userId;
    private final String name;
    private final Genders gender;
    private final LocalDate dateOfBirth;
    private final String email;
    private final String phone;
    private final List<Long> guardianIds; // avoid circular reference
}

