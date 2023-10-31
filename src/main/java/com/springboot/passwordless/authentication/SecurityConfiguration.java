package com.springboot.passwordless.authentication;

import com.bitwarden.passwordless.PasswordlessClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Autowired
    public PasswordlessClient passwordlessClient;

    @Bean
    public FilterRegistrationBean<PasswordlessAuthenticationFilter> filterRegistrationBean() {
        FilterRegistrationBean<PasswordlessAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PasswordlessAuthenticationFilter(passwordlessClient));

        // Specify the URL patterns to which the filter should be applied
        registrationBean.addUrlPatterns("/users/login");

        return registrationBean;
    }

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
        return http.build();
    }
}