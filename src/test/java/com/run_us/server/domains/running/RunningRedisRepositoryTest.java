package com.run_us.server.domain.running;

import com.run_us.server.config.TestRedisConfiguration;
import com.run_us.server.domain.running.model.ParticipantStatus;
import com.run_us.server.domain.running.repository.RunningRedisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@Import(TestRedisConfiguration.class)
@ActiveProfiles("test")
@DisplayName("RunningRedisRepository 테스트의")
class RunningRedisRepositoryTest {

  @Autowired
  RunningRedisRepository runningRedisRepository;

  @Autowired
  RedisTemplate<String, String> redisTemplate;

  @Nested
  @DisplayName("updateParticipantStatus 메소드는")
  class Describe_updateParticipantStatus {

    @Nested
    @DisplayName("참가자의 상태를 READY로 변경했을 때")
    class Context_with_a_participant {

      @DisplayName("참가자의 상태를 READY변경한다")
      @Test
      void it_updates_participant_status() {
        // given
        String key = "1";
        String value = "1";
        // when
        runningRedisRepository.updateParticipantStatus(key, value, ParticipantStatus.READY);

        // then
        String result = redisTemplate.opsForValue().get("running:1:1:status");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("READY", result);
      }
    }
  }

}
