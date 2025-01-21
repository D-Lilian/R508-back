/* (C)2025 */
package com.course.backend.service;

import com.course.backend.config.JwtUtil;
import com.course.backend.model.Professor;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class for JWT operations.
 */
@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    /**
     * Constructor for JwtService.
     *
     * @param jwtUtil JwtUtil object.
     */
    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Generates a token for a professor.
     *
     * @param user Professor object.
     * @return Token.
     */
    public String generateToken(Professor user) {
        return jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Set.of(
                                new org.springframework.security.core.authority
                                        .SimpleGrantedAuthority("PROFESSOR"))));
    }

    /**
     * Checks if a token is valid.
     *
     * @param token Token.
     * @param userDetails UserDetails object.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtUtil.isTokenValid(token, userDetails);
    }

    /**
     * Extracts the username from a token.
     *
     * @param token Token.
     * @return Username.
     */
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }
}
