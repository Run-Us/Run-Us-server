package com.run_us.server.domains.user.domain;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.run_us.server.domains.user.repository.PenaltyRepository;
import com.run_us.server.domains.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
class PenaltyRepositoryTest {

  @Autowired
  private PenaltyRepository penaltyRepository;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  @Test
  void create_penalty() {
    //given
    User user = UserFixtures.getDefaultUser();
    ReflectionTestUtils.setField(user, "id", 1);
    ReflectionTestUtils.setField(user.getProfile(), "userId", 1);
    userRepository.saveAndFlush(user);


    Penalty penalty = Penalty.builder()
        .penaltyType("PERMANENT_BAN")
        .description("banned")
        .expiresAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(TIME_ZONE_ID)))
        .build();
    //when
    User savedUser = userRepository.findByNickname("NICKNAME").get();
    penalty.applyPenaltyToUser(savedUser);
    penaltyRepository.save(penalty);

    List<Penalty> createdPenalty = penaltyRepository.findByUserId(savedUser.getId());

    //then
    assertFalse(createdPenalty.isEmpty());
    assertEquals(penalty, createdPenalty.get(0));
  }
}
