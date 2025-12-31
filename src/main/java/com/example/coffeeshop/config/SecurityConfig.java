package com.example.coffeeshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.coffeeshop.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth

                                                // PUBLIC
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/customer/signup").permitAll()

                                                // CUSTOMER
                                                .requestMatchers(HttpMethod.GET, "/api/products/**")
                                                .hasAnyRole("CUSTOMER", "MERCHANT", "ADMIN")

                                                // MERCHANT
                                                .requestMatchers("/api/merchant/**")
                                                .hasRole("MERCHANT")

                                                // ADMIN
                                                .requestMatchers("/api/admin/**")
                                                .hasRole("ADMIN")

                                                // DEFAULT
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
