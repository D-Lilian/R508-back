/* (C)2025 */
package com.course.backend.controller;

import com.course.backend.request.AuthRequest;
import com.course.backend.response.AuthResponse;
import com.course.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 * Controller for authentication
 */
@Tag(name = "Auth")
@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor
     *
     * @param authService service for authentication
     */
    public AuthController(AuthService authService) {
        super();
        this.authService = authService;
    }

    /**
     * Login
     * Rest Endpoint for login
     *
     * @param authRequest requested authentification data
     * @return ResponseEntity<AuthResponse> response entity with authentification data
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Authentification réussie"),
                @ApiResponse(
                        responseCode = "401",
                        description = "Authentification échouée",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Erreur serveur",
                        content =
                                @io.swagger.v3.oas.annotations.media.Content(
                                        schema =
                                                @io.swagger.v3.oas.annotations.media.Schema(
                                                        hidden = true)))
            })
    @Operation(summary = "Authentification d'un professeur")
    @PostMapping(value = "/auth/login", produces = "application/json")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(
                    authService.login(authRequest.getEmail(), authRequest.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
