/* (C)2025 */
package com.course.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Grade
 */
@Getter
@Setter
@Entity
@Table(name = "GRADE")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Student_Id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "Course_Id", nullable = false)
    private Course course;

    @JsonProperty("grade")
    @Column(name = "Grade", nullable = false, precision = 4)
    private double grade;

    @Column(name = "Semester", nullable = false, length = 2)
    private SEMESTER semester;

    @Column(name = "Academic_Year", nullable = false, length = 9)
    private String academicYear;

    @Column(name = "Created_At", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Grade constructor
     *
     * @param student      student
     * @param course       course
     * @param grade        grade
     * @param semester     semester
     * @param academicYear academic year
     */
    public Grade(
            Student student, Course course, double grade, String semester, String academicYear) {
        super();
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.semester = SEMESTER.fromString(semester);
        this.academicYear = academicYear;
    }

    /**
     * Grade default constructor
     */
    public Grade() {
        super();
    }
}
