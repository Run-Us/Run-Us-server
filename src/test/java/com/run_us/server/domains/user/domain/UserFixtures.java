package com.run_us.server.domains.user.domain;

import java.time.LocalDate;

public final class UserFixtures {

  public static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1999, 1, 1);

  public static User getDefaultUser() {
    User user = User.builder().build();
    Profile profile = Profile.builder()
            .userId(user.getId())
            .nickname("NICKNAME")
            .build();
    user.setProfile(profile);
    return user;
  }

  public static User getDefaultUserWithNickname(String nickname) {
    User user = User.builder().build();
    Profile profile = Profile.builder()
            .userId(user.getId())
            .nickname(nickname)
            .birthDate(DEFAULT_BIRTH_DATE)
            .gender(Gender.NONE)
            .build();
    user.setProfile(profile);
    return user;
  }
}
