package com.swp08.dpss.service.interfaces;

public interface TokenService {
    boolean resetPassword(String token, String newPassword);
}
