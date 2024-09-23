package com.run_us.server.domains.user.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

class UserTest {

  private static final String URL_FIXTURE = "img_URL";


  @Test
  @DisplayName("User 객체 생성 테스트")
  void create_user() {
    User user = UserFixtures.getDefaultUserWithNickname("nickname");
    assertEquals(user.getNickname(), "nickname");
    assertEquals(user.getBirthDate(), UserFixtures.DEFAULT_BIRTH_DATE);
  }

  @MethodSource("provideChangeUserProfileImgUrl")
  @DisplayName("User 프로필 이미지 변경")
  void change_user_profile_img_url() {
    //given
    User user = UserFixtures.getDefaultUser();
    String expectedImgUrl = URL_FIXTURE;
    //when
    user.changeProfileImgUrl(expectedImgUrl);

    //then
    assertEquals(user.getImgUrl(), expectedImgUrl);
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