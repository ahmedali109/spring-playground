package org.example.booting.auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final RedisSessionService redisSessionService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException{
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        if(! jwtService.isValid(token)){
            response.setStatus(401);
            return;
        }
        UUID userId = jwtService.extractUserId(token);
        String sessionId = jwtService.extractSessionId(token);
        String currentSession = redisSessionService.get(userId);
        if (currentSession == null || !currentSession.equals(sessionId)) {
            response.setStatus(401);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
