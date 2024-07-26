package com.exampleMicroservice.OrderService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.authorizeRequests(authorizeRequests-> authorizeRequests.
                  anyRequest()
                 .authenticated())
                  .oauth2ResourceServer(oAuth2ResourceServerConfigurer->
                          oAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

         return http.build();
    }
}

