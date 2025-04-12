package com.innovatech.solution.nomina.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfiguracion {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Permitir todas las peticiones
                )
                .formLogin(form -> form.disable())  // Deshabilitar login por formulario
                .httpBasic(basic -> basic.disable()); // Deshabilitar autenticación básica

        return http.build();
    }
}
