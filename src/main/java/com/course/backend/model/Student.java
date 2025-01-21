/* (C)2025 */
package com.course.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "STUDENT")
public class Student {

    @Schema(description = "ID auto-généré de l'étudiant")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "Id")
    private Long id;

    @Schema(description = "Prénom de l'étudiant")
    @JsonProperty("firstName")
    @Column(name = "First_Name", nullable = false, length = 100)
    private String firstName;

    @Schema(description = "Nom de l'étudiant")
    @JsonProperty("lastName")
    @Column(name = "Last_Name", nullable = false, length = 100)
    private String lastName;

    @Schema(description = "Email de l'étudiant", example = "user@example.com")
    @JsonProperty("email")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Schema(description = "Date de naissance de l'étudiant")
    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "Date_Of_Birth", nullable = false)
    @CreationTimestamp
    private LocalDate dateOfBirth;

    @Schema(description = "Numéro d'étudiant unique")
    @JsonProperty("studentId")
    @Column(name = "Student_Id", nullable = false, unique = true, length = 50)
    private String studentId;

    /**
     * Student constructor
     *
     * @param firstName first name of the student
     * @param lastName last name of the student
     * @param email email of the student
     * @param dateOfBirth date of birth of the student*
     * @param studentId student ID
     */
    public Student(
            String firstName,
            String lastName,
            String email,
            LocalDate dateOfBirth,
            String studentId) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.studentId = studentId;
    }

    /**
     * Student default constructor
     */
    public Student() {
        super();
    }
}
