/* (C)2025 */
package com.course.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Course statistics implementation
 */
@Getter
@Setter
public class CourseStatisticsImpl implements CourseStatistics {
    private String courseCode;
    private String courseName;
    private Double averageGrade;
    private Double minGrade;
    private Double maxGrade;
    private Long totalStudents;
    private Double successRate;

    /**
     * Constructor for course statistics
     *
     * @param courseCode course code
     * @param courseName course name
     * @param averageGrade average grade
     * @param minGrade minimum grade
     * @param maxGrade maximum grade
     * @param totalStudents total students
     * @param successRate success rate
     */
    public CourseStatisticsImpl(
            String courseCode,
            String courseName,
            Double averageGrade,
            Double minGrade,
            Double maxGrade,
            Long totalStudents,
            Double successRate) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.averageGrade = averageGrade;
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
        this.totalStudents = totalStudents;
        this.successRate = successRate;
    }

    /**
     * Default constructor
     */
    public CourseStatisticsImpl() {
        super();
    }
}
