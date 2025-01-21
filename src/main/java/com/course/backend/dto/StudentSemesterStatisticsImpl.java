/* (C)2025 */
package com.course.backend.dto;

import com.course.backend.model.SEMESTER;
import lombok.Getter;
import lombok.Setter;

/**
 * Student semester statistics implementation
 */
@Getter
@Setter
public class StudentSemesterStatisticsImpl implements StudentSemesterStatistics {
    private SEMESTER semester;
    private Double averageGrade;
    private Long totalCredits;
    private Long validatedCredits;
    private Long coursesCount;

    /**
     * Constructor for student semester statistics
     *
     * @param semester semester
     * @param averageGrade average grade
     * @param totalCredits total credits
     * @param validatedCredits validated credits
     * @param coursesCount courses count
     */
    public StudentSemesterStatisticsImpl(
            SEMESTER semester,
            Double averageGrade,
            Long totalCredits,
            Long validatedCredits,
            Long coursesCount) {
        this.semester = semester;
        this.averageGrade = averageGrade;
        this.totalCredits = totalCredits;
        this.validatedCredits = validatedCredits;
        this.coursesCount = coursesCount;
    }

    /**
     * Default constructor
     */
    public StudentSemesterStatisticsImpl() {
        super();
    }
}
