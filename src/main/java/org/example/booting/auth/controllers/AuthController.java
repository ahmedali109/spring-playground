package org.example.booting.auth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.booting.auth.dto.request.LoginRequest;
import org.example.booting.auth.dto.request.SignupRequest;
import org.example.booting.auth.dto.response.LoginResponse;
import org.example.booting.auth.dto.response.LogoutResponse;
import org.example.booting.auth.dto.response.SignupResponse;
import org.example.booting.auth.dto.response.UserResponse;
import org.example.booting.auth.service.AuthService;
import org.example.booting.auth.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        User user = authService.signup(signupRequest);

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getEmail()
        );

        SignupResponse response = new SignupResponse(
                "User Created",
                userResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){
        LoginResponse loginResponse = authService.login(loginRequest);
        Cookie cookie = new Cookie("token", loginResponse.token());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(new LogoutResponse("User logged out successfully"));
    }
}