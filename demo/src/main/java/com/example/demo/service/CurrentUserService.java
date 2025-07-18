package com.example.demo.service;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Service
@Getter
public class CurrentUserService {
    private final String username;
    private String userId;
    private String role;

    public CurrentUserService() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        this.username = authentication != null ? authentication.getName() : null;

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            this.userId = jwtAuth.getToken().getClaimAsString("userId");
            this.role = jwtAuth.getToken().getClaimAsString("roles");
        }
    }
}
