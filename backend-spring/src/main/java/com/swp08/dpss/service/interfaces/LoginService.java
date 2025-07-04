package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.LoginRequest;
import com.swp08.dpss.entity.client.User;

public interface LoginService {
    User login(LoginRequest loginRequest);
}
