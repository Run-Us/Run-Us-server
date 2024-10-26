package com.run_us.server.domains.user.domain;

import com.run_us.server.domains.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public final class UserFixtures {

  private final UserRepository userRepository;

  @Autowired
  public UserFixtures(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1999, 1, 1);

  public User getDefaultUser() {
    User user = User.builder().build();
    userRepository.save(user);

    Profile profile = Profile.builder()
            .userId(user.getId())
            .nickname("NICKNAME")
            .build();

    user.setProfile(profile);
    return user;
  }

  public User getDefaultUserWithNickname(String nickname) {
    User user = User.builder().build();
    userRepository.save(user);

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
