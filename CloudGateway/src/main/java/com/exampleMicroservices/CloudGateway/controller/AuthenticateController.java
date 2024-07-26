package com.exampleMicroservices.CloudGateway.controller;


import com.exampleMicroservices.CloudGateway.model.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticate")
public class  AuthenticateController {

    @Autowired
    private final JwtDecoder jwtDecoderr;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    // Inject JwtDecoder and JwtAuthenticationConverter
    public AuthenticateController( JwtDecoder jwtDecoderr, JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtDecoderr = jwtDecoderr;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @AuthenticationPrincipal OidcUser oidcUser,
            Model model,
            @RegisteredOAuth2AuthorizedClient("okta")
            OAuth2AuthorizedClient client){

        String accessToken = client.getAccessToken().getTokenValue();

        // Decode the access token into a Jwt object
        Jwt jwt = jwtDecoderr.decode(accessToken);
        List<String> roles = jwt.getClaimAsStringList("roles");
        AuthenticationResponse authenticationResponse= AuthenticationResponse.builder()
                .accessToken(client.getAccessToken().getTokenValue())
                .refreshToken(client.getRefreshToken().getTokenValue())
                .expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond() )
                .userId(oidcUser.getEmail())
                .rolesList(roles)
                .authorityList(oidcUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())).build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
