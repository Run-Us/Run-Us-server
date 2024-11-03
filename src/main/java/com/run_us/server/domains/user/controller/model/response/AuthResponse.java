package com.run_us.server.domains.user.controller.model.response;

import com.run_us.server.domains.user.domain.TokenPair;
import lombok.Getter;

@Getter
public class AuthResponse {
    private final boolean success;
    private final String accessToken;
    private final String refreshToken;
    private final String message;

    public AuthResponse(TokenPair tokenPair) {
        this.success = true;
        this.accessToken = tokenPair.accessToken();
        this.refreshToken = tokenPair.refreshToken();
        this.message = "Login success";
    }

    public AuthResponse(String errorMessage) {
        this.success = false;
        this.accessToken = null;
        this.refreshToken = null;
        this.message = errorMessage;
    }
}