/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.Student;
import com.course.backend.model.StudentInfo;
import com.course.backend.repository.StudentRepository;
import com.course.backend.request.StudentAddRequest;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class for Student
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructor for StudentService
     *
     * @param studentRepository Student repository
     */
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Get all students
     *
     * @return List of students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Add a student
     *
     * @param student Student object
     */
    public void addStudent(StudentAddRequest student) {
        studentRepository.save(
                new Student(
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        LocalDate.parse(student.getDateOfBirth()),
                        student.getStudentId()));
    }

    /**
     * Get a student by id
     *
     * @param id Student id
     * @return Student
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * Update a student
     *
     * @param studentId Student id
     */
    public StudentInfo getStudentInfo(Long studentId) {
        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new RuntimeException("Student not found"));
        return new StudentInfo(
                student.getFirstName(), student.getLastName(), student.getStudentId());
    }
}
