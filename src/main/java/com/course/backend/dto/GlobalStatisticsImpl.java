/* (C)2025 */
package com.course.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of the GlobalStatistics interface.
 */
@Getter
@Setter
public class GlobalStatisticsImpl implements GlobalStatistics {
    private Double globalAverage;
    private Long totalStudents;
    private Long totalCourses;
    private Double averageSuccessRate;

    /**
     * Constructor for the GlobalStatisticsImpl class.
     *
     * @param globalAverage The global average.
     * @param totalStudents The total number of students.
     * @param totalCourses The total number of courses.
     * @param averageSuccessRate The average success rate.
     */
    public GlobalStatisticsImpl(
            Double globalAverage,
            Long totalStudents,
            Long totalCourses,
            Double averageSuccessRate) {
        this.globalAverage = globalAverage;
        this.totalStudents = totalStudents;
        this.totalCourses = totalCourses;
        this.averageSuccessRate = averageSuccessRate;
    }

    /**
     * Default constructor.
     */
    public GlobalStatisticsImpl() {
        super();
    }
}
