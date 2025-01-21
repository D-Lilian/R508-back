/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.course.backend.BaseTest;
import com.course.backend.model.Student;
import com.course.backend.model.StudentInfo;
import com.course.backend.repository.StudentRepository;
import com.course.backend.request.StudentAddRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class StudentServiceTest extends BaseTest {

    @Mock private StudentRepository studentRepository;

    @InjectMocks private StudentService studentService;

    @Test
    public void testGetAllStudents() {
        Student student1 =
                new Student(
                        "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1), "S12345");
        Student student2 =
                new Student(
                        "Jane", "Doe", "jane.doe@example.com", LocalDate.of(2001, 2, 2), "S12346");
        List<Student> students = Arrays.asList(student1, student2);

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    public void testGetStudentById() {
        Student student =
                new Student(
                        "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1), "S12345");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    public void testAddStudent() {
        Student student =
                new Student(
                        "John", "Doe", "john.doe@example.com", LocalDate.of(2000, 1, 1), "S12345");

        when(studentRepository.save(student)).thenReturn(student);

        StudentAddRequest studentAddRequest =
                new StudentAddRequest(
                        "John", "Doe", "john.doe@example.com", "2000-01-01", "S12345");
        studentService.addStudent(studentAddRequest);
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    public void testGetStudentInfo() {
        Long studentId = 1L;
        Optional<Student> mockStudentInfo =
                Optional.of(
                        new Student(
                                "John",
                                "Doe",
                                "john.doe@mail.com",
                                LocalDate.of(2000, 1, 1),
                                "S12345"));

        when(studentRepository.findById(studentId)).thenReturn(mockStudentInfo);

        StudentInfo result = studentService.getStudentInfo(studentId);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("S12345", result.getStudentId());
    }
}
