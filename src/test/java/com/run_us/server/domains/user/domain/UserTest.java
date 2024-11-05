package com.run_us.server.domains.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

  private static final String URL_FIXTURE = "img_URL";


  @Test
  @DisplayName("User 객체 생성 테스트")
  void create_user() {
    User user = UserFixtures.getDefaultUserWithNickname("nickname");
    assertEquals(user.getProfile().getNickname(), "nickname");
    assertEquals(user.getProfile().getBirthDate(), UserFixtures.DEFAULT_BIRTH_DATE);
  }

  @Test
  @DisplayName("User 달리기 정보 수정")
  void update_user_running_info() {
    //given
    User user = UserFixtures.getDefaultUser();
    int distance = 1000;
    int duration = 1000;
    //when
    user.updateUserRunningInfo(distance, duration);
    //then
    Profile profile = user.getProfile();
    assertEquals(profile.getTotalDistance(), distance);
    assertEquals(profile.getTotalTime(), duration);
  }

  @Test
  @DisplayName("User 달리기 수정 시 최댓값만 갱신")
  void update_user_running_info_when_max() {
    //given
    User user = UserFixtures.getDefaultUser();
    int distance = 1000;
    int duration = 1000;
    //when
    user.updateUserRunningInfo(distance, duration);
    user.updateUserRunningInfo(distance * 2, 500);
    //then
    Profile profile = user.getProfile();
    assertEquals(profile.getLongestDistance(), distance * 2);
    assertEquals(profile.getLongestTime(), duration);
  }

  @Test
  @DisplayName("User soft delete")
  void remove_user() {
    //given
    User user = UserFixtures.getDefaultUser();

    //when
    user.remove();

    //then
    assertNotNull(user.getDeletedAt());
  }
}