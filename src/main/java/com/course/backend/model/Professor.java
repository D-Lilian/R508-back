/* (C)2025 */
package com.course.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Professor
 */
@Getter
@Setter
@Entity
@Table(name = "PROFESSOR")
public class Professor {

    @Schema(description = "ID auto-généré du professeur")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Schema(description = "Email du professeur", example = "user@example.com")
    @JsonProperty("email")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Schema(description = "Prénom du professeur")
    @JsonProperty("firstName")
    @Column(name = "First_Name", nullable = false, length = 100)
    private String firstName;

    @JsonIgnore
    @Column(name = "Last_Name", nullable = false, length = 100)
    private String lastName;

    @Schema(description = "Département du professeur")
    @JsonProperty("department")
    @Column(name = "Department", nullable = false, length = 100)
    private String department;

    @JsonIgnore
    @Column(name = "Password", nullable = false)
    private String password;

    /**
     * Professor constructor
     *
     * @param firstName  prénom
     * @param lastName   nom
     * @param email      email
     * @param department département
     * @param password   mot de passe
     */
    public Professor(
            String firstName, String lastName, String email, String department, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.password = password;
    }

    /**
     * Professor default constructor
     */
    public Professor() {
        super();
    }
}
