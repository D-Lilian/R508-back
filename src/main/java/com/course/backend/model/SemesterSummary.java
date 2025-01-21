/* (C)2025 */
package com.course.backend.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * SemesterSummary
 */
@Getter
@Setter
public class SemesterSummary {
    private String semester;
    private List<StudentGrade> grades;
    private double average;
    private int totalCredits;
    private int validatedCredits;

    /**
     * SemesterSummary constructor
     *
     * @param semester         semester
     * @param grades           list of grades
     * @param average          average grade
     * @param totalCredits     total credits
     * @param validatedCredits number of validate credits
     */
    public SemesterSummary(
            String semester,
            List<StudentGrade> grades,
            double average,
            int totalCredits,
            int validatedCredits) {
        super();
        this.semester = semester;
        this.grades = grades;
        this.average = average;
        this.totalCredits = totalCredits;
        this.validatedCredits = validatedCredits;
    }

    /**
     * SemesterSummary constructor
     */
    public SemesterSummary() {
        super();
    }
}
