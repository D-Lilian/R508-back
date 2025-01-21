/* (C)2025 */
package com.course.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.course.backend.EvalApplication;
import com.course.backend.ITBase;
import com.course.backend.model.Professor;
import com.course.backend.repository.ProfessorRepository;
import com.course.backend.request.AuthRequest;
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
            "spring.datasource.url=jdbc:mariadb:10.5.8:///iut_laval_grades"
                    + "database.populate.enabled=false"
        })
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AuthControllerIT extends ITBase {

    @Autowired private ProfessorRepository professorRepository;

    @Autowired private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        professorRepository.deleteAll();
    }

    @Test
    public void testLogin_Success() throws Exception {
        Professor professor =
                new Professor(
                        "John",
                        "Doe",
                        "prof@example.com",
                        "Informatique",
                        "$2a$10$yfPLBKnYK/whjcSCiPYpsO8uY3bgu.O79UZ4pzpyPix0ppMAaUIwy");
        professor.setId(1L);
        professorRepository.save(professor);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(professor.getEmail());
        authRequest.setPassword("password123");

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("wrongpassword");

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());
    }
}
