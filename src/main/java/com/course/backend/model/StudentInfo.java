/* (C)2025 */
package com.course.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInfo {
    private String firstName;
    private String lastName;
    private String studentId;

    /**
     * Constructor for StudentInfo
     *
     * @param firstName first name of the student
     * @param lastName last name of the student
     * @param studentId student id
     */
    public StudentInfo(String firstName, String lastName, String studentId) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
    }

    /**
     * Default constructor for StudentInfo
     */
    public StudentInfo() {
        super();
    }
}
