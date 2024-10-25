package com.run_us.server.domains.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.run_us.server.domains.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  @Test
  void create_user() {
    //given
    User user = UserFixtures.getDefaultUser();

    //when
    userRepository.save(user);
    Optional<Profile> createdUser = userRepository.findByNickname("NICKNAME");

    //then
    assertTrue(createdUser.isPresent());
    assertEquals("NICKNAME", createdUser.get().getNickname());
    assertNotNull(createdUser.get().getUser().getPublicId());
    assertEquals(user.getId(), createdUser.get().getId());
  }

  @Transactional
  @Test
  void remove_user() {
    //given
    User user = UserFixtures.getDefaultUser();
    userRepository.save(user);

    //when
    user.remove();
    Optional<Profile> removedUser = userRepository.findByNickname("NICKNAME");

    //then
    assertNotNull(user.getDeletedAt());
    assertTrue(removedUser.isEmpty());
  }

}
