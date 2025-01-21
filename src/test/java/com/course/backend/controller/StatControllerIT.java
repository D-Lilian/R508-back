/* (C)2025 */
package com.course.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.course.backend.EvalApplication;
import com.course.backend.ITBase;
import com.course.backend.model.Course;
import com.course.backend.model.Grade;
import com.course.backend.model.Student;
import com.course.backend.repository.CourseRepository;
import com.course.backend.repository.GradeRepository;
import com.course.backend.repository.StudentRepository;
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

@SpringBootTest(
        classes = EvalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "spring.datasource.url=jdbc:mariadb:10.5.8:///iut_laval_grades",
            "database.populate.enabled=false"
        })
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class StatControllerIT extends ITBase {

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
    public void testGetGlobalStatistics_Success() throws Exception {
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

        mockMvc.perform(
                        get("/api/stats/global")
                                .param("academicYear", "2022-2023")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCourseStatistics_Success() throws Exception {
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

        mockMvc.perform(
                        get("/api/stats/course/" + course.getId())
                                .param("academicYear", "2022-2023")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentSemesterStatistics_Success() throws Exception {
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

        mockMvc.perform(
                        get("/api/stats/student/" + student.getId())
                                .param("academicYear", "2022-2023")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
