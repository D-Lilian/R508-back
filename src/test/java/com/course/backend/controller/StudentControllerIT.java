/* (C)2025 */
package com.course.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.course.backend.EvalApplication;
import com.course.backend.ITBase;
import com.course.backend.model.Student;
import com.course.backend.repository.StudentRepository;
import com.course.backend.request.StudentAddRequest;
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
public class StudentControllerIT extends ITBase {

    @Autowired private StudentRepository studentRepository;

    @Autowired private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    public void testAddStudent_Success() throws Exception {
        StudentAddRequest studentAddRequest =
                new StudentAddRequest(
                        "John", "Doe", "john.doe@example.com", "2000-01-01", "S12345");

        mockMvc.perform(
                        post("/api/students")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(studentAddRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetStudents_Success() throws Exception {
        Student student =
                new Student(
                        "John",
                        "Doe",
                        "john.doe@example.com" + RandomString.make(20),
                        LocalDate.now(),
                        RandomString.make(20));
        studentRepository.save(student);

        mockMvc.perform(get("/api/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentById_Success() throws Exception {
        Student student =
                new Student(
                        "John",
                        "Doe",
                        "john.doe@example.com" + RandomString.make(20),
                        LocalDate.now(),
                        RandomString.make(20));
        studentRepository.save(student);

        mockMvc.perform(get("/api/students/" + student.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
