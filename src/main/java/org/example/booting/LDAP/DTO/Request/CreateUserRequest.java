package org.example.booting.LDAP.DTO.Request;

public record CreateUserRequest(
        String username,
        String password,
        String firstName,
        String lastName,
        String email
) {
}