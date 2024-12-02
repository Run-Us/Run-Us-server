package com.run_us.server.domains.user.controller.model.request;

import com.run_us.server.domains.user.domain.SocialProvider;
import com.run_us.server.global.validator.annotation.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthLoginRequest {
  @NotBlank private String oidcToken;

  @NotBlank
  @EnumValid(enumClass = SocialProvider.class)
  private String provider;
}
