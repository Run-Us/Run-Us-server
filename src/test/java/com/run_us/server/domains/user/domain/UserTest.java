package com.run_us.server.domains.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

@SpringBootTest
class UserTest {

  @Autowired
  private UserFixtures userFixtures;

  private static final String URL_FIXTURE = "img_URL";


  @Test
  @DisplayName("User 객체 생성 테스트")
  void create_user() {
    User user = userFixtures.getDefaultUserWithNickname("nickname");
    assertEquals(user.getProfile().getNickname(), "nickname");
    assertEquals(user.getProfile().getBirthDate(), UserFixtures.DEFAULT_BIRTH_DATE);
  }

  @MethodSource("provideChangeUserProfileImgUrl")
  @DisplayName("User 프로필 이미지 변경")
  void change_user_profile_img_url() {
    //given
    User user = userFixtures.getDefaultUser();
    Profile profile = new Profile().builder().imgUrl(URL_FIXTURE).build();
    String expectedImgUrl = URL_FIXTURE;
    //when
    user.setProfile(profile);

    //then
    assertEquals(user.getProfile().getImgUrl(), expectedImgUrl);
  }

  @Test
  @DisplayName("User soft delete")
  void remove_user() {
    //given
    User user = userFixtures.getDefaultUser();

    //when
    user.remove();

    //then
    assertNotNull(user.getDeletedAt());
  }
}