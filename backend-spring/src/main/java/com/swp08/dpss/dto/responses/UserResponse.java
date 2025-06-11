package com.swp08.dpss.dto.responses;

import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.Roles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private String name;
    private Roles role;
    private Genders gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private Set<ParentResponse>  parents;
}

