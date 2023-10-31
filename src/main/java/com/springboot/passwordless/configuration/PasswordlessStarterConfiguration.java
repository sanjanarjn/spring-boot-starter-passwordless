package com.springboot.passwordless.configuration;

import com.bitwarden.passwordless.PasswordlessClient;
import com.bitwarden.passwordless.PasswordlessClientBuilder;
import com.bitwarden.passwordless.PasswordlessOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordlessStarterConfiguration {

    @Bean
    public PasswordlessClient passwordlessClient(@Autowired PasswordlessRestConfiguration passwordlessApiConfig,
            @Autowired ObjectMapper objectMapper) {

        PasswordlessOptions passwordlessOptions = PasswordlessOptions.builder()
                .apiUrl(passwordlessApiConfig.getBaseUrl())
                .apiSecret(passwordlessApiConfig.getApiSecret())
                .build();
        return PasswordlessClientBuilder.create(passwordlessOptions)
                .objectMapper(objectMapper)
                .build();
    }
}