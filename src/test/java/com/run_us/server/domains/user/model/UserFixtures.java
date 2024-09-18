package com.run_us.server.domains.user.model;

import java.time.LocalDate;

public final class UserFixtures {

  public static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1999, 1, 1);

  public static User getDefaultUser() {
    return User.builder()
        .nickname("NICKNAME")
        .birthDate(DEFAULT_BIRTH_DATE)
        .gender(Gender.NONE)
        .build();
  }

  public static User getDefaultUserWithNickname(String nickname) {
    return User.builder()
        .nickname(nickname)
        .birthDate(DEFAULT_BIRTH_DATE)
        .gender(Gender.NONE)
        .build();
  }
}
