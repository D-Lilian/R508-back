/* (C)2025 */
package com.course.backend.config;

import com.course.backend.service.JwtService;
import com.course.backend.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The Jwt auth filter.
 * It is a filter that intercepts all requests and checks if the JWT token is valid.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Instantiates a new Jwt auth filter.
     *
     * @param jwtService         the jwt service
     * @param userDetailsService the user details service
     */
    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        super();
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Do filter internal.
     * It extracts the JWT token from the Authorization header and validates it.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var authToken =
                        new org.springframework.security.authentication
                                .UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}
