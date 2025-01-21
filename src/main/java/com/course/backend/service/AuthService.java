/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.Professor;
import com.course.backend.repository.ProfessorRepository;
import com.course.backend.response.AuthResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication-related operations
 */
@Service
public class AuthService {

    private final ProfessorRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Constructor for the AuthService class
     *
     * @param userRepository   The repository for users
     * @param passwordEncoder The password encoder
     * @param jwtService      The service for JWT-related operations
     */
    public AuthService(
            ProfessorRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Log in a user
     *
     * @param email     The email of the user
     * @param password  The password of the user
     * @return An AuthResponse object
     */
    public AuthResponse login(String email, String password) {
        Professor user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponse(jwtService.generateToken(user), user);
    }
}
