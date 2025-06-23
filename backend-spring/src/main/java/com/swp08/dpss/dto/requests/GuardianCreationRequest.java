package com.swp08.dpss.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuardianCreationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String guardianName;

    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email cannot be blank")
    private String guardianEmail;

    @NotBlank(message = "Phone cannot be blank")
    private String guardianPhone;
}
