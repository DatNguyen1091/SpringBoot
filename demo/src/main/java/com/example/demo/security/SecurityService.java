package com.example.demo.security;

import com.example.demo.constant.PermissionsConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component("SecurityService")
public class SecurityService {
    public boolean hasViewPermission(String resource) {
        return hasPermission(resource, "VIEW");
    }
    public boolean hasCreatePermission(String resource) {
        return hasPermission(resource, "CREATE");
    }
    public boolean hasUpdatePermission(String resource) {
        return hasPermission(resource, "UPDATE");
    }
    public boolean hasDeletePermission(String resource) {
        return hasPermission(resource, "DELETE");
    }

    private boolean hasPermission(String resource, String pre){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken jwtToken) {
            Set<String> setPermissions = new HashSet<>(jwtToken.getToken().getClaimAsStringList("permissions"));
            return setPermissions.contains(PermissionsConstant.Role.View);
        }
        return false;
    }
}
