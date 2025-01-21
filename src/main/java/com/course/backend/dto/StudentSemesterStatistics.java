/* (C)2025 */
package com.course.backend.dto;

import com.course.backend.model.SEMESTER;

/**
 * Student semester statistics
 */
public interface StudentSemesterStatistics {
    SEMESTER getSemester();

    Double getAverageGrade();

    Long getTotalCredits();

    Long getValidatedCredits();

    Long getCoursesCount();
}
