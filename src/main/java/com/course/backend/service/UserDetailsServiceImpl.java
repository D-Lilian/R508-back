/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.Professor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class is used to load user-specific data.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ProfessorService professorService;

    /**
     * Constructor
     *
     * @param professorService an instance of ProfessorService
     */
    public UserDetailsServiceImpl(ProfessorService professorService) {
        this.professorService = professorService;
    }

    /**
     * This method is used to load user-specific data.
     *
     * @param email the email of the user
     * @return an instance of UserDetails
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Professor professor =
                professorService
                        .findUserByUsername(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Professor not found"));
        return org.springframework.security.core.userdetails.User.withUsername(professor.getEmail())
                .password(professor.getPassword())
                .build();
    }
}
