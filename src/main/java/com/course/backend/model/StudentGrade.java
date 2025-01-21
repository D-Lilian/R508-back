/* (C)2025 */
package com.course.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGrade {

    private String courseCode;
    private String courseName;
    private int credits;
    private double grade;
    private String semester;

    /**
     * Student Grade constructor
     *
     * @param courseCode code of the course
     * @param courseName name of the course
     * @param credits    number of credits for the course
     * @param grade      grade obtained in the course
     * @param semester   semester in which the course was taken
     */
    public StudentGrade(
            String courseCode, String courseName, int credits, double grade, SEMESTER semester) {
        super();
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.grade = grade;
        this.semester = semester.toString();
    }

    /**
     * Student default constructor
     */
    public StudentGrade() {
        super();
    }
}
