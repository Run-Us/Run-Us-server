package com.run_us.server.domains.user.controller;

import com.run_us.server.domains.user.controller.model.request.*;
import com.run_us.server.domains.user.controller.model.response.AuthResponse;
import com.run_us.server.domains.user.domain.*;
import com.run_us.server.domains.user.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        AuthResult authResult = userAuthService.authenticateOAuth(
                request.getOidcToken(),
                SocialProvider.valueOf(request.getProvider())
        );

        if (authResult.type() == AuthResultType.LOGIN_SUCCESS) {
            return ResponseEntity.ok(new AuthResponse(authResult.tokenPair()));
        } else if (authResult.type() == AuthResultType.SIGNUP_REQUIRED) {
            return ResponseEntity.status(403).body(new AuthResponse("Signup required"));
        } else {
            return ResponseEntity.status(400).body(new AuthResponse("Authentication failed"));
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupAndLogin(@Valid @RequestBody AuthSignupRequest request) {
        AuthResult authResult = userAuthService.signupAndLogin(
                request.getOidcToken(),
                SocialProvider.valueOf(request.getProvider()),
                request.toProfile()
        );

        if (authResult.type() == AuthResultType.LOGIN_SUCCESS) {
            return ResponseEntity.ok(new AuthResponse(authResult.tokenPair()));
        } else {
            return ResponseEntity.status(400).body(new AuthResponse("Signup failed"));
        }
    }
}