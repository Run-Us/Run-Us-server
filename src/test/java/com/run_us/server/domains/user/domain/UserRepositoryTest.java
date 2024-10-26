package com.run_us.server.domains.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.run_us.server.domains.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  @Test
  void create_user() {
    //given
    User user = UserFixtures.getDefaultUserWithNickname("CREATED_USER");
    ReflectionTestUtils.setField(user, "id", 500);
    ReflectionTestUtils.setField(user.getProfile(), "userId", 500);

    //when
    userRepository.save(user);
    Optional<User> createdUser = userRepository.findByNickname("CREATED_USER");

    //then
    assertTrue(createdUser.isPresent());
    assertEquals("CREATED_USER", createdUser.get().getProfile().getNickname());
    assertNotNull(createdUser.get().getPublicId());
  }

  @Transactional
  @Test
  void remove_user() {
    //given
    User user = UserFixtures.getDefaultUserWithNickname("REMOVED_USER");
    ReflectionTestUtils.setField(user, "id", 1);
    ReflectionTestUtils.setField(user.getProfile(), "userId", 1);
    user.remove();
    userRepository.save(user);

    //when
    Optional<User> removedUser = userRepository.findByNickname("REMOVED_USER");

    //then
    assertNotNull(user.getDeletedAt());
    assertTrue(removedUser.isEmpty());
  }

}
