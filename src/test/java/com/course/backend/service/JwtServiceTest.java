/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.course.backend.BaseTest;
import com.course.backend.config.JwtUtil;
import com.course.backend.model.Professor;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceTest extends BaseTest {

    @Mock private JwtUtil jwtUtil;

    @InjectMocks private JwtService jwtService;

    @Test
    public void testGenerateToken() {
        Professor professor = new Professor();
        professor.setEmail("test@example.com");
        professor.setPassword("password");

        UserDetails userDetails =
                new User(
                        professor.getEmail(),
                        professor.getPassword(),
                        Set.of(
                                new org.springframework.security.core.authority
                                        .SimpleGrantedAuthority("PROFESSOR")));
        String expectedToken = "jwtToken";

        when(jwtUtil.generateToken(userDetails)).thenReturn(expectedToken);

        String token = jwtService.generateToken(professor);

        assertEquals(expectedToken, token);
        verify(jwtUtil, times(1)).generateToken(userDetails);
    }

    @Test
    public void testIsTokenValid() {
        String token = "jwtToken";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtUtil.isTokenValid(token, userDetails)).thenReturn(true);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
        verify(jwtUtil, times(1)).isTokenValid(token, userDetails);
    }

    @Test
    public void testExtractUsername() {
        String token = "jwtToken";
        String expectedUsername = "test@example.com";

        when(jwtUtil.extractUsername(token)).thenReturn(expectedUsername);

        String username = jwtService.extractUsername(token);

        assertEquals(expectedUsername, username);
        verify(jwtUtil, times(1)).extractUsername(token);
    }
}
