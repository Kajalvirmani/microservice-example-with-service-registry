package com.exampleMicroService.PaymentService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests-> authorizeRequests
                        .requestMatchers("/payment/**")
                        .hasAuthority("SCOPE_internal").anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2Server-> oauth2Server.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
