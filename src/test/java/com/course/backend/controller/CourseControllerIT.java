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
import com.course.backend.repository.CourseRepository;
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
public class CourseControllerIT extends ITBase {

    @Autowired private CourseRepository courseRepository;

    @Autowired private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    public void testAddCourse_Success() throws Exception {
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");

        mockMvc.perform(
                        post("/api/courses")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(course)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetCourses_Success() throws Exception {
        Course course =
                new Course("R1.1", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);
        course = new Course("R1.2", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);

        mockMvc.perform(get("/api/courses").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCourse_Success() throws Exception {
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);

        Course updatedCourse = new Course("CS101", "Computer Science", 5, "Updated Description");

        mockMvc.perform(
                        put("/api/courses/" + course.getId())
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(updatedCourse)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCourse_Success() throws Exception {
        Course course =
                new Course("CS101", "Computer Science", 5, "Introduction to Computer Science");
        courseRepository.save(course);

        Long id = 0L;
        for (Course c : courseRepository.findAll()) {
            id = c.getId();
        }

        mockMvc.perform(delete("/api/courses/" + id.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
