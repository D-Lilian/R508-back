/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.course.backend.BaseTest;
import com.course.backend.model.Professor;
import com.course.backend.repository.ProfessorRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ProfessorServiceTest extends BaseTest {

    @Mock private ProfessorRepository professorRepository;

    @InjectMocks private ProfessorService professorService;

    @Test
    public void testFindUserByUsername_UserExists() {
        String email = "test@example.com";
        Professor professor = new Professor();
        professor.setEmail(email);

        when(professorRepository.findByEmail(email)).thenReturn(Optional.of(professor));

        Optional<Professor> result = professorService.findUserByUsername(email);

        assertTrue(result.isPresent());
        assertEquals(professor, result.get());
        verify(professorRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindUserByUsername_UserDoesNotExist() {
        String email = "test@example.com";

        when(professorRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Professor> result = professorService.findUserByUsername(email);

        assertTrue(result.isEmpty());
        verify(professorRepository, times(1)).findByEmail(email);
    }
}
