package com.swp08.dpss.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Create a JWT for a given user
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 10)) // 10 seconds for test
                .signWith(key, SignatureAlgorithm.HS256) // 512 bits = 64 bytes for HS512
                .compact();
    }

    // Pull username (subject) from JWT
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getSubject();
    }

    // Check if token is valid and not expired
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        // You can also validate the token here if needed
        // For example:
        // if (jwtUtil.isTokenExpired(jwt)) {
        //     throw new RuntimeException("Token has expired");
        // }
        // return true;
        // Or you can use the extractUsername method to validate the username
    }

    // Check if token's expiration is past
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}
