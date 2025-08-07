package com.swp08.dpss.dto.requests.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class GuardianCreationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String guardianName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String guardianEmail;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid phone number, phone number must be between 10 and 15 digits")
    private String guardianPhone;

//    @NotEmpty(message = "At least one User ID is required")
    private Set<Long> userIds; // Optional for user path, required for admin path

}
