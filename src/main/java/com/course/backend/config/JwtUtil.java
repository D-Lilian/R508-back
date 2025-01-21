/* (C)2025 */
package com.course.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The type Jwt util.
 * It provides utility methods to extract username, expiration date, and generate JWT token.
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            Base64.getEncoder().encodeToString("6NV1qc1w5VIGz7x7mGooIxgeg5cyKtnF".getBytes());

    /**
     * Extract username.
     * It extracts the username from the JWT token.
     *
     * @param token the token
     * @return the string
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract claim t.
     * It extracts the claim from the JWT token.
     *
     * @param <T>            the type parameter
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @return the t
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims.
     * It extracts all the claims from the JWT token.
     *
     * @param token the token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Is token valid boolean.
     * It checks if the JWT token is valid.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return the boolean
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Is token expired boolean.
     * It checks if the JWT token is expired.
     *
     * @param token the token
     * @return the boolean
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract expiration date.
     * It extracts the expiration date from the JWT token.
     *
     * @param token the token
     * @return the date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generate token string.
     * It generates a JWT token.
     *
     * @param userDetails the user details
     * @return the string
     */
    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    /**
     * Create token string.
     * It creates a JWT token.
     *
     * @param subject the subject
     * @return the string
     */
    private String createToken(String subject) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
