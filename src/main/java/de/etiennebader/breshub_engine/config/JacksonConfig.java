package de.etiennebader.breshub_engine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Beispielkonfiguration: Einschalten der Pretty Printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
