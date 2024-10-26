package com.run_us.server.domains.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.run_us.server.domains.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import(UserFixtures.class)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserFixtures userFixtures;

  @Transactional
  @Test
  void create_user() {
    //given
    User user = userFixtures.getDefaultUser();

    //when
    userRepository.save(user);
    Optional<User> createdUser = userRepository.findByNickname("NICKNAME");

    //then
    assertTrue(createdUser.isPresent());
    assertEquals("NICKNAME", createdUser.get().getProfile().getNickname());
    assertNotNull(createdUser.get().getPublicId());
    assertEquals(user.getId(), createdUser.get().getId());
  }

  @Transactional
  @Test
  void remove_user() {
    //given
    User user = userFixtures.getDefaultUser();
    userRepository.save(user);

    //when
    user.remove();
    Optional<User> removedUser = userRepository.findByNickname("NICKNAME");

    //then
    assertNotNull(user.getDeletedAt());
    assertTrue(removedUser.isEmpty());
  }

}
