package com.run_us.server.domain.user.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

class UserTest {

  private static final String URL_FIXTURE = "img_URL";

  User getUserFixture() {
   return new User("nickname", LocalDate.of(2000, 4, 21));
  }

  @Test
  @DisplayName("User 객체 생성 테스트")
  void create_user() {
    User user = new User("nickname", LocalDate.of(2000, 4, 21));
    assertEquals(user.getNickname(), "nickname");
    assertEquals(user.getBirthDate(), LocalDate.of(2000, 4, 21));
  }

  @MethodSource("provideChangeUserProfileImgUrl")
  @DisplayName("User 프로필 이미지 변경")
  void change_user_profile_img_url() {
    //given
    User user = getUserFixture();
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
    User user = getUserFixture();

    //when
    user.remove();

    //then
    assertNotNull(user.getDeletedAt());
  }
}