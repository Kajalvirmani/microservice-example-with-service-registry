package com.exampleMicroservices.CloudGateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class OktaOauth2WebSecurity {

    @Bean
    public JwtDecoder jwtDecoderBean() {

        return NimbusJwtDecoder.withIssuerLocation("https://dev-21624739.okta.com/oauth2/default").build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterBean() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Configure converter if necessary
        return converter;
    }
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity){
       httpSecurity.authorizeExchange(
               exchange-> exchange.anyExchange().authenticated()
               ).oauth2Login(Customizer.withDefaults())
               .oauth2ResourceServer(oAuth2ResourceServerSpec ->
                       oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()
                       ));

       return httpSecurity.build();
    }

}
