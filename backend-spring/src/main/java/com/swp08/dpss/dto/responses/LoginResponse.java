package com.swp08.dpss.dto.responses;

import com.swp08.dpss.enums.Roles;
import java.util.List;

public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Roles primaryRole; // The role stored in the database
    private List<String> effectiveRoles; // All roles the user effectively has (for UI and Spring Security later)
    private String token; // Placeholder for a JWT or session token

    public LoginResponse(Long id, String name, String email, Roles primaryRole, List<String> effectiveRoles, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.primaryRole = primaryRole;
        this.effectiveRoles = effectiveRoles;
        this.token = token;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(Roles primaryRole) {
        this.primaryRole = primaryRole;
    }

    public List<String> getEffectiveRoles() {
        return effectiveRoles;
    }

    public void setEffectiveRoles(List<String> effectiveRoles) {
        this.effectiveRoles = effectiveRoles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
