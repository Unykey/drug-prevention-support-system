package com.swp08.dpss.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private UserResponse user;
}
