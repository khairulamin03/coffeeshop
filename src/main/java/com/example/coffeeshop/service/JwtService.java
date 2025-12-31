package com.example.coffeeshop.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
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
    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles",
                        userDetails.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + properties.getExpiration()))
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(
                properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
