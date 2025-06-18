package com.swp08.dpss.dto.requests;

import com.swp08.dpss.enums.Genders;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ParentCreationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String parentName;

    @Email(message = "Email should be vaild")
    @NotBlank(message = "Email cannot be blank")
    private String parentEmail;

    @NotBlank(message = "Phone cannot be blank")
    private String parentPhone;
}
