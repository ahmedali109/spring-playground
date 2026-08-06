package org.example.booting.auth.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record SignupRequest(

        @NotBlank
        @Email
        String email ,

        @NotBlank
        @Size(min = 8 , message = "Password must be at least 8 characters")
        String password
) { }
