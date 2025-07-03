package com.swp08.dpss.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//@Data
@Getter
@AllArgsConstructor
public class AuthRequest {
    private final String email;
    private final String password;


}
