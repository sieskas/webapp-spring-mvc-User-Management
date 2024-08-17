package com.example.usermanagementapp.api.v1.rest.config;

import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.service.TokenService;
import com.example.usermanagementapp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    public TokenAuthenticationFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            String token = request.getParameter("token");

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Basic ")) {
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(decodedBytes);
                StringTokenizer tokenizer = new StringTokenizer(credentials, ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();


                User user = (User) userService.loadUserByUsername(username);
                if (user.getUsername().equals(username) && tokenService.validateToken(password, request.getServletPath())) {
                    SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(password));
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader("WWW-Authenticate", "Basic realm=\"example\"");
                    response.getWriter().write("Unauthorized: Invalid username or token.");
                    return;
                }
            } else if (token != null && tokenService.validateToken(token, request.getServletPath())) {
                SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(token));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("WWW-Authenticate", "Basic realm=\"example\"");
                response.getWriter().write("Unauthorized: Invalid or missing token.");
                return;
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
