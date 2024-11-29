package com.run_us.server.domains.user.controller;

import com.run_us.server.domains.user.controller.model.request.*;
import com.run_us.server.domains.user.controller.model.response.AuthResponse;
import com.run_us.server.domains.user.controller.model.response.UserHttpResponseCode;
import com.run_us.server.domains.user.domain.*;
import com.run_us.server.domains.user.service.UserAuthService;
import com.run_us.server.global.common.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserAuthService userAuthService;

  @PreAuthorize("permitAll()")
  @PostMapping("/login")
  public SuccessResponse<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
    AuthResult authResult =
        userAuthService.authenticateOAuth(
            request.getOidcToken(), SocialProvider.valueOf(request.getProvider()));
    return SuccessResponse.of(
        UserHttpResponseCode.LOGIN_SUCCESS, new AuthResponse(authResult.tokenPair()));
  }

  @PreAuthorize("permitAll()")
  @PostMapping("/signup")
  public SuccessResponse<AuthResponse> signupAndLogin(
      @Valid @RequestBody AuthSignupRequest request) {
    AuthResult authResult =
        userAuthService.signupAndLogin(
            request.getOidcToken(),
            SocialProvider.valueOf(request.getProvider()),
            request.toProfile());
    return SuccessResponse.of(
        UserHttpResponseCode.SIGNUP_SUCCESS, new AuthResponse(authResult.tokenPair()));
  }
}
