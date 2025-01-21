/* (C)2025 */
package com.course.backend.repository;

import com.course.backend.model.Professor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The interface Professor repository.
 * This interface is used to interact with the database.
 */
@RepositoryRestResource(exported = false)
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    /**
     * Find by email optional.
     * This method is used to find a professor by email.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Professor> findByEmail(String email);
}
