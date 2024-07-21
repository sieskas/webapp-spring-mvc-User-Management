package com.example.usermanagementapp.api.v1.rest.config;

import com.example.usermanagementapp.service.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws java.io.IOException, javax.servlet.ServletException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwtService.validateToken(jwt)) {
            SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(jwt));
        }
        filterChain.doFilter(request, response);
    }
}