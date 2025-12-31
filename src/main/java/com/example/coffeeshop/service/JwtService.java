package com.example.coffeeshop.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.coffeeshop.security.CustomUserDetails;
import com.example.coffeeshop.security.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final JwtProperties properties;

    public JwtService(JwtProperties properties) {
        this.properties = properties;

        if (properties.getSecret() == null) {
            throw new IllegalStateException("JWT secret is not configured!");
        }
    }

    // 🔐 GENERATE TOKEN
    public String generateToken(CustomUserDetails user) {

        return Jwts.builder()
                .setSubject(user.getUsername()) // email (principal)
                .claim("role", user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + properties.getExpiration()))
                .signWith(
                        Keys.hmacShaKeyFor(properties.getSecret().getBytes()))
                .compact();
    }

    // 🔓 EXTRACT EMAIL
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ VALIDASI TOKEN
    public boolean isTokenValid(String token) {
        return getClaims(token)
                .getExpiration()
                .after(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(properties.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
