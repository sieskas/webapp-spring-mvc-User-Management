//package com.example.usermanagementapp.config;
//
//import com.example.usermanagementapp.api.v1.rest.config.TokenAuthenticationFilter;
//import com.example.usermanagementapp.service.TokenService;
//import com.example.usermanagementapp.service.UserService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity()
//@Order(1)
//public class ApiSecurityConfig {
//
//    private final TokenService tokenService;
//    private final UserService userService;
//
//    public ApiSecurityConfig(TokenService tokenService, UserService userService) {
//        this.tokenService = tokenService;
//        this.userService = userService;
//    }
//
//    @Bean
//    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/api/**")
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .csrf(AbstractHttpConfigurer::disable);
//
//        http.addFilterBefore(new TokenAuthenticationFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}