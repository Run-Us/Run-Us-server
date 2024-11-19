package com.run_us.server.domains.user.controller.model.response;

import com.run_us.server.domains.user.domain.TokenPair;
import lombok.Getter;

@Getter
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;

    public AuthResponse(TokenPair tokenPair) {
        this.accessToken = tokenPair.accessToken();
        this.refreshToken = tokenPair.refreshToken();
    }
}