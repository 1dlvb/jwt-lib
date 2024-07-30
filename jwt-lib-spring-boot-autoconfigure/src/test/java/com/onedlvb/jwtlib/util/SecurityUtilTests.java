package com.onedlvb.jwtlib.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityUtilTests {

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testHasRoleWhenUserHasRole() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Roles.USER.name()));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertTrue(SecurityUtil.hasRole(Roles.USER));
    }

    @Test
    void testHasRoleWhenUserDoesNotHaveProperRole() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SOME_OTHER"));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertFalse(SecurityUtil.hasRole(Roles.USER));
    }

    @Test
    void testHasRoleWhenAuthenticationIsNull() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(SecurityUtil.hasRole(Roles.USER));
    }

    @Test
    void testHasRoleWhenAuthoritiesAreEmpty() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertFalse(SecurityUtil.hasRole(Roles.USER));
    }
}
