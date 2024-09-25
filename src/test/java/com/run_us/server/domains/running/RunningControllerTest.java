package com.run_us.server.domains.running;

import com.run_us.server.config.TestRedisConfiguration;
import com.run_us.server.domains.running.controller.JoinedParticipantsDto;
import com.run_us.server.domains.running.controller.RunningController;
import com.run_us.server.domains.running.domain.Running;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.running.service.RunningPreparationService;
import com.run_us.server.domains.user.model.User;
import com.run_us.server.domains.user.model.UserFixtures;
import com.run_us.server.domains.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestRedisConfiguration.class)
@ActiveProfiles("test")
@DisplayName("RunningController 테스트의")
class RunningControllerTest {

  @Autowired
  private RunningController runningController;

  @Autowired
  private RunningPreparationService runningPreparationService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RunningRepository runningRepository;

  @Nested
  @DisplayName("fetchRunningParticipants 메소드는")
  class Describe_fetchRunningParticipants {

    private Running r1;

    @BeforeEach
    void setUp() {
      // save users
      User u1 = UserFixtures.getDefaultUserWithNickname("us1");
      User u2 = UserFixtures.getDefaultUserWithNickname("us2");
      userRepository.saveAndFlush(u1);
      userRepository.saveAndFlush(u2);

      // create running
      r1 = RunningFixtures.getDefaultRunningWithParticipants(List.of(u1, u2));
      runningRepository.saveAndFlush(r1);
    }

    @DisplayName("참가자 목록을 반환한다")
    @Test
    void it_returns_participants() {
      // given
      String runningId = r1.getPublicKey();
      // when
      List<JoinedParticipantsDto> joinedParticipants = runningPreparationService.getJoinedParticipants(
          runningId);
      // then
      Assertions.assertFalse(joinedParticipants.isEmpty());
      Assertions.assertEquals(2, joinedParticipants.size());
    }
  }

}
