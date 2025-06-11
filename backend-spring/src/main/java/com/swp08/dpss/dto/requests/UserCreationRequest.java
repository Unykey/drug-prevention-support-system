package com.swp08.dpss.dto.requests;

import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;

//    Default = MEMBER
    @NotNull(message = "Invalid role. Allowed roles are MEMBER, CONSULTANT, STAFF, ADMIN, MANAGER.")
    private Roles role;

//    Default = PREFER_NOT_TO_STAY
    private Genders gender;

    @NotNull(message = "Date of Birth cannot be blank")
    private LocalDate dateOfBirth;

    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;
}
