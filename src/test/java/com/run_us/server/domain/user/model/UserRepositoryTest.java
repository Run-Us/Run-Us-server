package com.run_us.server.domain.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.run_us.server.domain.user.repository.UserRepository;
import java.time.LocalDate;
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
    User user = new User("nickname", LocalDate.now());

    //when
    userRepository.save(user);
    Optional<User> createdUser = userRepository.findByNickname("nickname");

    //then
    assertEquals(user, createdUser.get());
    assertNotNull(createdUser.get().getPublicId());
  }

  @Transactional
  @Test
  void remove_user() {
    //given
    User user = new User("nickname", LocalDate.now());
    userRepository.save(user);

    //when
    user.remove();
    Optional<User> savedUser = userRepository.findByNickname("nickname");

    //then
    assertNotNull(user.getDeletedAt());
    assertTrue(savedUser.isEmpty());
  }

}
