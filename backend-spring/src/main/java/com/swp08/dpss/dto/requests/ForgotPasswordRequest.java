package com.swp08.dpss.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForgotPasswordRequest {
    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
