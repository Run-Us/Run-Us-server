package com.run_us.server.global.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import com.run_us.server.global.common.ErrorResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final  JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String authPrefix = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(authPrefix)) {
            setErrorResponse(UserErrorCode.JWT_NOT_FOUND, response);
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        try {
            String publicId = jwtService.getUserIdFromAccessToken(jwt);
            request.setAttribute("publicUserId", publicId);
        } catch (TokenExpiredException e) {
            setErrorResponse(UserErrorCode.JWT_EXPIRED, response);
            return;
        } catch (Exception e) {
            setErrorResponse(UserErrorCode.JWT_BROKEN, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(UserErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}