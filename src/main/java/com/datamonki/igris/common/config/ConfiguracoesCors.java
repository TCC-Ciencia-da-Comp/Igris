package com.datamonki.igris.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracoesCors {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**") // Configura todos os endpoints que começam com /api/
                        .allowedOrigins("http://localhost:3000") // Permite a origem do React 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") // Permite todos os cabeçalhos
                        .exposedHeaders("Set-Cookie") // Expõe o cabeçalho "Set-Cookie" ao cliente
                        .allowCredentials(true); // Permite o envio de cookies
            }
        };
    }
}
