package de.etiennebader.breshub_engine.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://88.151.194.71:8084/", description = "Local Server"),
                @Server(url = "https://breshub-engine.etiennebader.de", description = "Public URL")
        }
)
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {

        SecurityScheme securityScheme = new SecurityScheme() //
                .type(SecurityScheme.Type.HTTP) //
                .scheme("bearer") //
                .bearerFormat("JWT") //
                .name("Authorization");
        Components components = new Components().addSecuritySchemes("bearerAuth", securityScheme);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info().title("BresHub Engine")
                .description("API test for BresHub Engine, which is the Backend of the webapp BresHub"))
                .components(components).addSecurityItem(securityRequirement);
    }

}
