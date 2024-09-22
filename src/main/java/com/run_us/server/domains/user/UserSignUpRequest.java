package com.run_us.server.domains.user;

import lombok.Getter;

@Getter
public class UserSignUpRequest {
  private final String nickName;
  private final String email;

  public UserSignUpRequest(String nickName, String email) {
    this.nickName = nickName;
    this.email = email;
  }
}
