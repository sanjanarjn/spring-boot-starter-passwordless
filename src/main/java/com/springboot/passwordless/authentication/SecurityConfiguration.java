package com.springboot.passwordless.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    public PasswordlessAuthenticationFilter passwordlessAuthenticationFilter;

    @Bean
    public Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer() {
        return csrf -> csrf
                .disable();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrfCustomizer()).authorizeHttpRequests((auth) -> auth
                .requestMatchers("/users/login", "/users/register").permitAll()
                .anyRequest().authenticated()
        );
        http.addFilterBefore(passwordlessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}