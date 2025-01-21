/* (C)2025 */
package com.course.backend.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for updating a grade
 */
@Getter
@Setter
@Schema(description = "Requête de mise à jour de note")
public class GradeUpdateRequest {

    @Schema(description = "Note sur 20", example = "20", minimum = "0", maximum = "20")
    private double grade;

    /**
     * Constructor
     *
     * @param grade grade
     */
    public GradeUpdateRequest(double grade) {
        this.grade = grade;
    }

    /**
     * Default constructor
     */
    public GradeUpdateRequest() {
        super();
    }
}
