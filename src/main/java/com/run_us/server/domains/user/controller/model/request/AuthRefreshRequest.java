package com.run_us.server.domains.user.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRefreshRequest {
    @NotBlank
    private String refreshToken;
}