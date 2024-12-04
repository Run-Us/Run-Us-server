package com.run_us.server.global.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.service.JwtService;
import com.run_us.server.global.common.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String AUTH_HEADER = "Authorization";
  private static final String AUTH_PREFIX = "Bearer ";
  private static final int TOKEN_PREFIX_LENGTH = 7;

  private final JwtService jwtService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    if (!hasValidAuthorizationHeader(request)) {
      setErrorResponse(UserErrorCode.JWT_NOT_FOUND, response);
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = extractToken(request);
    if (!processToken(jwt, request, response)) {
      return;
    }
    filterChain.doFilter(request, response);
  }

  private boolean hasValidAuthorizationHeader(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTH_HEADER);
    return authHeader != null && authHeader.startsWith(AUTH_PREFIX);
  }

  private String extractToken(HttpServletRequest request) {
    return request.getHeader(AUTH_HEADER).substring(TOKEN_PREFIX_LENGTH);
  }

  private boolean processToken(String jwt, HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String publicId = jwtService.getUserIdFromAccessToken(jwt);
      request.setAttribute("publicUserId", publicId);
      return true;
    } catch (TokenExpiredException e) {
      setErrorResponse(UserErrorCode.JWT_EXPIRED, response);
    } catch (Exception e) { // JWT 손상 시 오류
      setErrorResponse(UserErrorCode.JWT_BROKEN, response);
    }
    return false;
  }

  private void setErrorResponse(UserErrorCode errorCode, HttpServletResponse response) throws IOException {
    response.setStatus(errorCode.getHttpStatusCode().value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    ErrorResponse errorResponse = ErrorResponse.of(errorCode);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
