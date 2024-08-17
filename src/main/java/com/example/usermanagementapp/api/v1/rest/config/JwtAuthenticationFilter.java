package com.example.usermanagementapp.api.v1.rest.config;

import com.example.usermanagementapp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws java.io.IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwtService.validateToken(jwt)) {
            SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(jwt));
        }
        filterChain.doFilter(request, response);
    }
}