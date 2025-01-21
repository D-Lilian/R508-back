/* (C)2025 */
package com.course.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.course.backend.EvalApplication;
import com.course.backend.ITBase;
import com.course.backend.model.Course;
import com.course.backend.model.Grade;
import com.course.backend.model.Student;
import com.course.backend.repository.CourseRepository;
import com.course.backend.repository.GradeRepository;
import com.course.backend.repository.StudentRepository;
import com.course.backend.request.GradeAddRequest;
import java.time.LocalDate;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(
        classes = EvalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "spring.datasource.url=jdbc:mariadb:10.5.8:///iut_laval_grades",
            "database.populate.enabled=false"
        })
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class GradeControllerIT extends ITBase {

    @Autowired private GradeRepository gradeRepository;

    @Autowired private StudentRepository studentRepository;

    @Autowired private CourseRepository courseRepository;

    @Autowired private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    public void testAddGrade_Success() throws Exception {
        Student student =
                new Student(
                        "John",
                        "Doe",
                        "john.doe@example.com" + RandomString.make(20),
                        LocalDate.now(),
                        RandomString.make(20));
        studentRepository.save(student);
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);

        GradeAddRequest gradeAddRequest =
                new GradeAddRequest(student.getId(), course.getId(), 15.0, "S1", "2022-2023");

        mockMvc.perform(
                        post("/api/grades")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(gradeAddRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetGrades_Success() throws Exception {
        Student student =
                new Student(
                        "John",
                        "Doe",
                        "john.doe@example.com" + RandomString.make(20),
                        LocalDate.now(),
                        RandomString.make(20));
        studentRepository.save(student);
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);
        Grade grade = new Grade(student, course, 15.0, "S1", "2022-2023");
        gradeRepository.save(grade);

        mockMvc.perform(get("/api/grades").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGradesByStudent_Success() throws Exception {
        Student student =
                new Student("John", "Doe", "john.doe@example.com", LocalDate.now(), "123456");
        studentRepository.save(student);
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);
        Grade grade = new Grade(student, course, 15.0, "S1", "2022-2023");
        gradeRepository.save(grade);

        mockMvc.perform(
                        get("/api/grades/student/" + student.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateGrade_Success() throws Exception {
        Student student =
                new Student(
                        "John",
                        "Doe",
                        "john.doe@example.com" + RandomString.make(20),
                        LocalDate.now(),
                        RandomString.make(20));
        studentRepository.save(student);
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);
        Grade grade = new Grade(student, course, 15.0, "S1", "2022-2023");
        gradeRepository.save(grade);

        double updatedGrade = 18.0;

        mockMvc.perform(
                        put("/api/grades/" + grade.getId())
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(updatedGrade)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteGrade_Success() throws Exception {
        Student student =
                new Student(
                        "John",
                        "Doe",
                        "john.doe@example.com" + RandomString.make(20),
                        LocalDate.now(),
                        RandomString.make(20));
        studentRepository.save(student);
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);
        Grade grade = new Grade(student, course, 15.0, "S1", "2022-2023");
        gradeRepository.save(grade);

        mockMvc.perform(delete("/api/grades/" + grade.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGenerateTranscript_Success() throws Exception {
        Student student =
                new Student("John", "Doe", "john.doe@example.com", LocalDate.now(), "123456");
        studentRepository.save(student);
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);
        Grade grade = new Grade(student, course, 15.0, "S1", "2022-2023");
        gradeRepository.save(grade);

        mockMvc.perform(
                        get("/api/grades/student/" + student.getId() + "/transcript")
                                .param("academicYear", "2022-2023")
                                .accept(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk());
    }
}
