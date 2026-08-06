package org.example.booting.auth.dto.response;

public record LoginResponse (String message , UserResponse user , String token) {}
