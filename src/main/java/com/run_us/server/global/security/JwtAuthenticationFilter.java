package com.run_us.server.global.security;

import com.run_us.server.domains.user.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String authPrefix = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(authPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        try {
            String publicId = jwtService.getUserIdFromAccessToken(jwt);
            request.setAttribute("publicUserId", publicId);
        } catch (Exception e) {
            logger.error("Unable to set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
}