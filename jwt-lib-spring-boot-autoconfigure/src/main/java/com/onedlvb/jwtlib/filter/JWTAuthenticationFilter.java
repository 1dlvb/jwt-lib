package com.onedlvb.jwtlib.filter;

import com.onedlvb.jwtlib.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * JWTAuthenticationFilter is a filter that intercepts incoming HTTP requests to validate JWT tokens.
 * @author Matushkin Anton
 */
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @NonNull
    private JWTUtil jwtUtil;

    /**
     * Filters incoming HTTP requests to validate the JWT token.
     * <p>
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an IO error occurs during filtering
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null && !jwtUtil.isTokenExpired(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            List<String> roles = jwtUtil.getRolesFromToken(token);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "",
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * @param request the HTTP request
     * @return token, extracted from the request
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

}
