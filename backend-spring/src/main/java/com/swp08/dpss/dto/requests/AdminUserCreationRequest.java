package com.swp08.dpss.dto.requests;

import com.swp08.dpss.enums.Genders;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminUserCreationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Role cannot be blank")
    @Pattern(regexp = "MEMBER|STAFF|CONSULTANT|MANAGER|ADMIN", message = "Invalid role")
    private String role;

    private Genders gender; // Default will be handled in service if null

    @NotNull(message = "Date of Birth cannot be blank")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid phone number, phone number must be between 10 and 15 digits")
    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotNull(message = "Status cannot be blank")
    @Pattern(regexp = "PENDING|VERIFIED|SUSPENDED|EXPIRED|LOCKED|BANNED|DELETED", message = "Invalid status")
    private String status;

    //  Require 1 Guardian if user_age < 18
    @Valid
    private GuardianCreationRequest guardian;
}
