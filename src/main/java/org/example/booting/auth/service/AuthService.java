package org.example.booting.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.booting.auth.dto.request.LoginRequest;
import org.example.booting.auth.dto.request.SignupRequest;
import org.example.booting.auth.user.User;
import org.example.booting.auth.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User signup(SignupRequest signupRequest){
        if(userRepository.findByEmail(signupRequest.email()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(signupRequest.email());
        user.setPassword(passwordEncoder.encode(signupRequest.password()));
        return userRepository.save(user);
    }

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow();
        if(!passwordEncoder.matches(
                loginRequest.password(),
                user.getPassword()
        )){
            throw new RuntimeException("Wrong password");
        }
        return jwtService.generateAccessToken(user);
    }
}