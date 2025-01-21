/* (C)2025 */
package com.course.backend.dto;

/**
 * Course statistics
 */
public interface CourseStatistics {
    String getCourseCode();

    String getCourseName();

    Double getAverageGrade();

    Double getMinGrade();

    Double getMaxGrade();

    Long getTotalStudents();

    Double getSuccessRate();
}
