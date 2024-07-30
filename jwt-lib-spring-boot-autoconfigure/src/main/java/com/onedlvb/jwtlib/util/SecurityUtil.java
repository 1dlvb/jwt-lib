package com.onedlvb.jwtlib.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Utility class for security operations.
 * @author Matushkin Anton
 */
public final class SecurityUtil {

    private SecurityUtil() {}

    /**
     * Checks if the current user has the specified role.
     * @param role The role to check for.
     * @return true if the current user has the specified role, otherwise false.
     */
    public static boolean hasRole(Roles role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role.name()));
    }

}
