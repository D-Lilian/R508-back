/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.course.backend.BaseTest;
import com.course.backend.model.Professor;
import com.course.backend.repository.ProfessorRepository;
import com.course.backend.response.AuthResponse;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceTest extends BaseTest {

    @Mock private ProfessorRepository professorRepository;

    @Mock private PasswordEncoder passwordEncoder;

    @Mock private JwtService jwtService;

    @InjectMocks private AuthService authService;

    private AutoCloseable closeable;

    @Test
    public void testLoginSuccess() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String token = "jwtToken";

        Professor professor = new Professor();
        professor.setEmail(email);
        professor.setPassword(encodedPassword);

        when(professorRepository.findByEmail(email)).thenReturn(Optional.of(professor));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(professor)).thenReturn(token);

        AuthResponse response = authService.login(email, password);

        assertEquals(token, response.getToken());
        assertEquals(professor, response.getProfessor());

        verify(professorRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(jwtService, times(1)).generateToken(professor);
    }

    @Test
    public void testLoginUserNotFound() {
        String email = "test@example.com";
        String password = "password";

        when(professorRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> authService.login(email, password));
        assertEquals("User not found", exception.getMessage());

        verify(professorRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    public void testLoginInvalidPassword() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        Professor professor = new Professor();
        professor.setEmail(email);
        professor.setPassword(encodedPassword);

        when(professorRepository.findByEmail(email)).thenReturn(Optional.of(professor));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> authService.login(email, password));
        assertEquals("Invalid password", exception.getMessage());

        verify(professorRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(jwtService, never()).generateToken(any());
    }
}
