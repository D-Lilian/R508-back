/* (C)2025 */
package com.course.backend.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the response of a grade retrieval request.
 */
@Getter
@Setter
@Schema(description = "Requête d'ajout de note")
public class GradeGetResponse {

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

    @Schema(description = "Prénom de l'étudiant")
    private String studentFirstName;

    @Schema(description = "Nom de l'étudiant")
    private String studentLastName;

    @Schema(description = "Code unique du cours")
    private String courseCode;

    @Schema(description = "Nom du cours")
    private String courseName;

    /**
     * Constructor with parameters.
     *
     * @param id               The grade ID.
     * @param studentId        The student ID.
     * @param courseId         The course ID.
     * @param grade            The grade.
     * @param semester         The semester.
     * @param academicYear     The academic year.
     * @param studentFirstName The student first name.
     * @param studentLastName  The student last name.
     * @param courseCode       The course code.
     * @param courseName       The course name.
     */
    public GradeGetResponse(
            Long id,
            Long studentId,
            Long courseId,
            double grade,
            String semester,
            String academicYear,
            String studentFirstName,
            String studentLastName,
            String courseCode,
            String courseName) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.semester = semester;
        this.academicYear = academicYear;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    /**
     * Default constructor.
     */
    public GradeGetResponse() {
        super();
    }
}
