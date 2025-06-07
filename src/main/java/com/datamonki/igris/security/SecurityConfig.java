package com.datamonki.igris.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableMethodSecurity // Habilita o uso de @PreAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Adiciona suporte a CORS
            .csrf(csrf -> csrf.disable()) // Desativa CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll() // Endpoints públicos
                .requestMatchers("/admin/**").hasRole("ADMIN") // Apenas para admins
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Para usuários ou admins
                .anyRequest().authenticated() // Qualquer outra rota requer autenticação
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuração de CORS
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Permite o front-end local
        config.addAllowedHeader("*"); // Permite todos os cabeçalhos
        config.addAllowedMethod("*"); // Permite todos os métodos (GET, POST, etc.)
        config.setAllowCredentials(true); // Permite envio de cookies/credenciais
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Permite o front-end local
        config.addAllowedHeader("*"); // Permite todos os cabeçalhos
        config.addAllowedMethod("*"); // Permite todos os métodos
        config.setAllowCredentials(true); // Permite o envio de cookies
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    // Registrar o PermissionEvaluator customizado
    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomPermissionEvaluator();
    }
}
