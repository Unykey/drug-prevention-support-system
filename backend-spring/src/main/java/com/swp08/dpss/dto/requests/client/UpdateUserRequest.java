package com.swp08.dpss.dto.requests.client;

import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.enums.User_Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    // password, email, phone and guardian will be updated with separate API call
    private String name;

    private Genders gender; //Default = PREFER_NOT_TO_STAY

    private LocalDate dateOfBirth;

    private User_Status status; // ADMIN-only
    private Roles role; // ADMIN-only
}
