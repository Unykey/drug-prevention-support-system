package com.swp08.dpss.dto.requests.client;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;
    private String token;
}
