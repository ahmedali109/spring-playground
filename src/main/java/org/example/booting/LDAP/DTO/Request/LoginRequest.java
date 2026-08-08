package org.example.booting.LDAP.DTO.Request;

public record LoginRequest(
        String username,
        String password
) {
}