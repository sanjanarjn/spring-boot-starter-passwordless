package com.springboot.passwordless.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix ="passwordless.rest")
@Data
public class PasswordlessRestConfiguration {
    private String baseUrl;
    private String apiKey;
    private String apiSecret;
}