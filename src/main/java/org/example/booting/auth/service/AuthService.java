package org.example.booting.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.booting.auth.dto.request.LoginRequest;
import org.example.booting.auth.dto.request.SignupRequest;
import org.example.booting.auth.dto.response.LoginResponse;
import org.example.booting.auth.dto.response.UserResponse;
import org.example.booting.auth.user.User;
import org.example.booting.auth.user.UserRepository;
import org.example.booting.exceptions.EmailAlreadyExistsException;
import org.example.booting.exceptions.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisSessionService redisSessionService;

    public User signup(SignupRequest signupRequest){
        if (userRepository.findByEmail(signupRequest.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        User user = new User();
        user.setEmail(signupRequest.email());
        user.setPassword(passwordEncoder.encode(signupRequest.password()));
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow();
        if(!passwordEncoder.matches(
                loginRequest.password(),
                user.getPassword()
        )){
            throw new InvalidCredentialsException();
        }
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail());
        String sessionID = UUID.randomUUID().toString();
        redisSessionService.save(user.getId() , sessionID);
        String accessToken = jwtService.generateAccessToken(user.getId() , sessionID);
        String refreshToken = jwtService.generateRefreshToken(user.getId() , sessionID);
        return new LoginResponse("user logged in successfully" , userResponse , accessToken , refreshToken);
    }
}