/* (C)2025 */
package com.course.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration.
 * This class sets up the OpenAPI configuration.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Custom open api.
     * It sets the server URL and description.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("/");
        server.setDescription("Serveur API");
        return new OpenAPI()
                .servers(List.of(server))
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("API R508-back")
                                .description("Documentation de l'API pour le site R508-front")
                                .version("1.0"));
    }
}
