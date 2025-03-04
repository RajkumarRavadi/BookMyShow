package com.bookmyshow.BMSproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow all requests
                .httpBasic(httpBasic -> httpBasic.disable()) // Disable basic authentication
                .formLogin(form -> form.disable()); // Disable form login

        return http.build();
    }
}
