package com.example.demoparkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String JTW_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 5;

    private JwtUtils(){

    }
    // Generates a secret key for signing JWTs
    private static javax.crypto.SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    // Calculates the expiration date for a JWT
    private static Date toExpireDate(Date start){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }
    // Creates a JWT for a user with a specific role
    public static JwtToken createToken(String username, String role) {
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);
        String token = Jwts.builder()
                .header().add("typ", "JWT") // Adds a header indicating the type of the token
                .and()
                .subject(username) // Sets the subject of the token (usually the user)
                .issuedAt(issuedAt) // Set the issuance date of the token
                .expiration(limit) // Sets the expiration date of the token
                .signWith(generateKey()) // Signs the token with a generated key
                .claim("role", role)  // Adds a claim indicating the user's role
                .compact(); // Compact the token into a String
        return new JwtToken(token);
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey()) // Verifies the token with a generated key
                    .build()
                    .parseSignedClaims(refactorToken(token)).getPayload(); // Parses the token and get the payload (claims)
        } catch (JwtException ex) {
            log.error(String.format("Invalid token %s", ex.getMessage()));
        }
        return null;
    }
    // Extracts the username from a JWT
    public static String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }
    // Checks if a JWT is valid
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token));
            return true;
        } catch (JwtException ex) {
            log.error(String.format("Invalid token %s", ex.getMessage()));
        }
        return false;
    }
    // Refactors a JWT by removing the "Bearer " prefix
    private static String refactorToken(String token) {
        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

}
