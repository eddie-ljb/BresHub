package de.etiennebader.breshub_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan(basePackages = "de.etiennebader.breshub_engine.dao")

public class BreshubEngineApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BreshubEngineApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
		app.run(args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Allow CORS on all paths
						.allowedOrigins("https://breshub-engine.etiennebader.de")
						.allowedOrigins("http://breshub-engine.etiennebader.de")// Allow specific origin
						.allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific methods
						.allowedHeaders("*") // Allow all headers
						.allowCredentials(true);
			}
		};
	}

}
