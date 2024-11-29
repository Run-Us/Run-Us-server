package com.run_us.server.domains.user.controller;

import com.run_us.server.domains.user.controller.model.response.AuthResponse;
import com.run_us.server.domains.user.domain.TokenPair;
import com.run_us.server.domains.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("dev")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
class TestAuthController {

  private final UserAuthService userAuthService;

  @PreAuthorize("permitAll()")
  @PostMapping("/auth/users")
  public ResponseEntity<AuthResponse> createTestUserToken(@RequestBody Integer internalId) {
    TokenPair tokenPair = userAuthService.devLogin(internalId);
    return ResponseEntity.ok(new AuthResponse(tokenPair));
  }
}
