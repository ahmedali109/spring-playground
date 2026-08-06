package org.example.booting.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.booting.JWTProperties;
import org.example.booting.auth.user.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final JWTProperties JWTProperties;
    private String secret;

    public JwtService(JWTProperties jwtProperties){
        this.JWTProperties = jwtProperties;
        this.secret = JWTProperties.getSecret();
    }

    public String generateAccessToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWTProperties.getAccessExpiration()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }
}