package com.studhub.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Value("${secret}")
    private String secret;

    @Bean
    public PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder(secret);
    }
}
