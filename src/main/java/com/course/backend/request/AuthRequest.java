/* (C)2025 */
package com.course.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the authentication request.
 */
@Getter
@Setter
@Schema(description = "Requête d'authentification")
public class AuthRequest {

    @Schema(description = "Email du professeur", example = "user@example.com")
    private String email;

    @Schema(description = "Mot de passe du professeur")
    private String password;

    /**
     * Constructor
     *
     * @param email    email of the user
     * @param password password of the user
     */
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Default constructor
     */
    public AuthRequest() {
        super();
    }
}
