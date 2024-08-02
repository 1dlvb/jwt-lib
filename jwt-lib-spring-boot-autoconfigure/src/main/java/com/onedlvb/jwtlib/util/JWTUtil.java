package com.onedlvb.jwtlib.util;

import com.onedlvb.jwtlib.config.JwtLibProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for handling JWT operations such as generating and validating tokens.
 * @author Matushkin Anton
 */
@RequiredArgsConstructor
public class JWTUtil {

    @NonNull
    private final JwtLibProperties properties;

    private SecretKey key;

    @PostConstruct
    private void init() {
        byte[] secretBytes = Base64.getDecoder().decode(properties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(secretBytes, properties.getAlgorithm());
    }

    /**
     * Extracts the username from the given token.
     * @param token The token.
     * @return The username.
     */
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracts the roles from the given token.
     * @param token The token.
     * @return list of roles.
     */
    public List<String> getRolesFromToken(String token) {
        if (isTokenExpired(token)) {
            return Collections.emptyList();
        } else {
            return extractClaims(token, claims -> claims.get("roles", List.class));
        }

    }

    /**
     * Checks if the given token is expired.
     * @param token The token.
     * @return True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        try {
            return extractClaims(token, Claims::getExpiration).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * Validates the token against the user details.
     * @param token The token.
     * @param userDetails The user details.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extracts claims from the given token using the provided function.
     * @param token The token.
     * @param claimsTFunction The function to apply on the claims.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) throws ExpiredJwtException {
        return claimsTFunction.apply(Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload());

    }

}
