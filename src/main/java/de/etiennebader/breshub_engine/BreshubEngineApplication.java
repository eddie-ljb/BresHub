package de.etiennebader.breshub_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan(basePackages = "de.etiennebader.breshub_engine.dao")

public class BreshubEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreshubEngineApplication.class, args);
	}

}
