/* (C)2025 */
package com.course.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for adding a student
 */
@Getter
@Setter
@Schema(description = "Requête d'ajout d'étudiant")
public class StudentAddRequest {

    @Schema(description = "ID auto-généré de la requête")
    private Long id;

    @Schema(description = "Prénom de l'étudiant")
    private String firstName;

    @Schema(description = "Nom de l'étudiant")
    private String lastName;

    @Schema(description = "Email de l'étudiant")
    private String email;

    @Schema(description = "Date de naissance de l'étudiant")
    private String dateOfBirth;

    @Schema(description = "ID auto-généré de l'étudiant")
    private String studentId;

    /**
     * Constructor
     *
     * @param firstName prénom de l'étudiant
     * @param lastName nom de l'étudiant
     * @param email email de l'étudiant
     * @param dateOfBirth date de naissance de l'étudiant
     * @param studentId ID auto-généré de l'étudiant
     */
    public StudentAddRequest(
            String firstName, String lastName, String email, String dateOfBirth, String studentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.studentId = studentId;
    }

    /**
     * Default constructor
     */
    public StudentAddRequest() {
        super();
    }
}