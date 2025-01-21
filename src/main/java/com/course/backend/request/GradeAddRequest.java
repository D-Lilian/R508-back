/* (C)2025 */
package com.course.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for adding a grade
 */
@Getter
@Setter
@Schema(description = "Requête d'ajout de note")
public class GradeAddRequest {

    @Schema(description = "ID auto-généré de la note")
    private Long id;

    @Schema(description = "ID de l'étudiant")
    private Long studentId;

    @Schema(description = "ID du cours")
    private Long courseId;

    @Schema(description = "Note sur 20", example = "20")
    private double grade;

    @Schema(description = "Semestre", example = "S1")
    private String semester;

    @Schema(description = "Année académique", example = "2024-2025")
    private String academicYear;

    /**
     * Constructor
     *
     * @param studentId    student ID
     * @param courseId     course ID
     * @param grade        grade
     * @param semester     semester
     * @param academicYear academic year
     */
    public GradeAddRequest(
            Long studentId, Long courseId, double grade, String semester, String academicYear) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    /**
     * Default constructor
     */
    public GradeAddRequest() {
        super();
    }
}
