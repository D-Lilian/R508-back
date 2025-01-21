/* (C)2025 */
package com.course.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Class to run the application.
 */
@SpringBootApplication(scanBasePackages = "com.course.backend")
@EnableJpaRepositories(basePackages = "com.course.backend.repository")
public class EvalApplication {

    /**
     * Main method to run the application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(EvalApplication.class, args);
    }
}
