package com.java360.pmanager.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveMemberDataDTO {

    @NotNull(message = "Name cannot be empty")
    @Size(min = 1, max = 80, message = "Invalid member name")
    private final String name;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid is not a valid email")
    private final String email;
}
