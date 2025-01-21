/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.course.backend.BaseTest;
import com.course.backend.model.Professor;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImplTest extends BaseTest {

    @Mock private ProfessorService professorService;

    @InjectMocks private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername_UserExists() {
        String email = "test@example.com";
        Professor professor = new Professor();
        professor.setEmail(email);
        professor.setPassword("password");

        when(professorService.findUserByUsername(email)).thenReturn(Optional.of(professor));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals(professor.getPassword(), userDetails.getPassword());
        verify(professorService, times(1)).findUserByUsername(email);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "test@example.com";

        when(professorService.findUserByUsername(email)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));
        verify(professorService, times(1)).findUserByUsername(email);
    }
}
