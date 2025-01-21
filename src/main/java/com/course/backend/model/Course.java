/* (C)2025 */
package com.course.backend.model;

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
 * Course
 */
@Getter
@Setter
@Entity
@Table(name = "COURSE")
public class Course {

    @Schema(description = "ID auto-généré du cours")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "Id")
    private Long id;

    @Schema(description = "Code unique du cours")
    @JsonProperty("code")
    @Column(name = "Code", nullable = false, unique = true, length = 20)
    private String code;

    @Schema(description = "Nom du cours")
    @JsonProperty("name")
    @Column(name = "Name", nullable = false)
    private String name;

    @Schema(description = "Nombre de crédits ECTS")
    @JsonProperty("credits")
    @Column(name = "Credits", nullable = false)
    private int credits;

    @Schema(description = "Description détaillée du cours")
    @JsonProperty("description")
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    /**
     * Course constructor
     *
     * @param code        course code
     * @param name        course name
     * @param credits     course credits
     * @param description course description
     */
    public Course(String code, String name, int credits, String description) {
        super();
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.description = description;
    }

    /**
     * Course default constructor
     */
    public Course() {
        super();
    }
}
