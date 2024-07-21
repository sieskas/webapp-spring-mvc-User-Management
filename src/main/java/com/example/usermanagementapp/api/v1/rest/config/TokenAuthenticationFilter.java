package com.example.usermanagementapp.api.v1.rest.config;

import com.example.usermanagementapp.service.TokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token != null && tokenService.validateToken(token, request.getServletPath())) {
            SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(token));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Basic realm=\"example\"");
            response.getWriter().write("Unauthorized: Invalid or missing token.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}