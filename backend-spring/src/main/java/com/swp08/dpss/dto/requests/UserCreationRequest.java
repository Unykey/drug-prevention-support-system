package com.swp08.dpss.dto.requests;

import com.swp08.dpss.enums.Genders;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserCreationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    private Genders gender; //Default = PREFER_NOT_TO_STAY

    @NotNull(message = "Date of Birth cannot be blank")
    private LocalDate dateOfBirth;

    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

//  Require 1 Guardian if user_age < 18
    @Valid
    private GuardianCreationRequest guardian;
}
