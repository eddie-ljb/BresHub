package de.etiennebader.breshub_engine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("BresHub Engine")
                .description("API test for BresHub Engine, which is the Backend of the webapp BresHub"));
    }

}
