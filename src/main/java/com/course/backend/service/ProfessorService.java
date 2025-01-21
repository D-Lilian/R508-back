/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.Professor;
import com.course.backend.repository.ProfessorRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for the Professor entity.
 */
@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    /**
     * Constructor for the ProfessorService class.
     *
     * @param professorRepository the repository for the Professor entity.
     */
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    /**
     * Method to find a user by their username.
     *
     * @param email the username of the user.
     * @return an Optional of the user.
     */
    public Optional<Professor> findUserByUsername(String email) {
        return professorRepository.findByEmail(email);
    }
}
