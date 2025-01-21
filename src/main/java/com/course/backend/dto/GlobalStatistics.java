/* (C)2025 */
package com.course.backend.dto;

/**
 * Interface for global statistics.
 */
public interface GlobalStatistics {
    Double getGlobalAverage();

    Long getTotalStudents();

    Long getTotalCourses();

    Double getAverageSuccessRate();
}
