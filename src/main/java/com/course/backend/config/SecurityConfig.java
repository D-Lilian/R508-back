/* (C)2025 */
package com.course.backend.config;

import com.course.backend.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Security configuration class
 * Configure security filter chain, authentication provider, authentication manager, password encoder
 */
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Constructor
     *
     * @param userDetailsService User details service
     * @param jwtAuthFilter      JWT authentication filter
     */
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Security filter chain
     * Disable CSRF, form login, HTTP basic authentication
     * Add JWT authentication filter
     * Authorize requests
     * Handle exceptions
     * Set headers
     * Configure CORS
     *
     * @param http Http security
     * @return Security filter chain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(
                                                "api/auth/login",
                                                "swagger-ui.html",
                                                "/swagger-ui/*",
                                                "/v3/api-docs/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .exceptionHandling(
                        exceptions ->
                                exceptions.authenticationEntryPoint(
                                        (request, response, authException) -> {
                                            response.setContentType("application/json");
                                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                            response.getWriter()
                                                    .write("{\"error\": \"Unauthorized\"}");
                                        }))
                .headers(
                        headers ->
                                headers.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .cors(
                        cors ->
                                cors.configurationSource(
                                        request -> {
                                            CorsConfiguration config = new CorsConfiguration();
                                            config.setAllowCredentials(true);
                                            config.addAllowedOriginPattern("*");
                                            config.addAllowedHeader("*");
                                            config.addAllowedMethod("*");
                                            return config;
                                        }));
        return http.build();
    }

    /**
     * Dao authentication provider
     * Set user details service and password encoder
     *
     * @return Dao authentication provider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Authentication manager
     * Set authentication configuration
     *
     * @param config Authentication configuration
     * @return Authentication manager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Password encoder
     * Use BCrypt password encoder
     *
     * @return Password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
