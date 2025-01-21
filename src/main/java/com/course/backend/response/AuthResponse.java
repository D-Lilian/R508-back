/* (C)2025 */
package com.course.backend.response;

import com.course.backend.model.Professor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the response of an authentication request.
 */
@Getter
@Setter
@Schema(description = "Réponse d'authentification")
public class AuthResponse {

    @Schema(description = "Token JWT")
    private String token;

    @Schema(description = "Utilisateur", implementation = Professor.class)
    private Professor professor;

    /**
     * Constructor with parameters.
     *
     * @param token     The JWT token.
     * @param professor The professor.
     */
    public AuthResponse(String token, Professor professor) {
        super();
        this.token = token;
        this.professor = professor;
    }

    /**
     * Default constructor.
     */
    public AuthResponse() {
        super();
    }
}
