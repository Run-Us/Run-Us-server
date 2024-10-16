package com.run_us.server.domains.running;

import static org.junit.jupiter.api.Assertions.*;

import com.run_us.server.domains.running.domain.running.Running;
import com.run_us.server.domains.running.domain.running.RunningConstraints;
import com.run_us.server.domains.running.domain.running.RunningDescription;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserFixtures;
import com.run_us.server.global.util.PointGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("Running 클래스의")
class RunningTest {
  @Nested
  @DisplayName("생성자는")
  class Describe_constructor {

    @Nested
    @DisplayName("유효한 인자를 받았을 때")
    class Context_with_valid_arguments {
      @Test
      @DisplayName("Running 객체를 생성한다")
      void it_creates_a_running_object() {
        Point startLocation = PointGenerator.generatePoint(0L, 0L);
        RunningConstraints constraints = RunningFixtures.getDefaultRunningConstraints();
        RunningDescription description = RunningFixtures.getDefaultRunningDescription();
        Running running = new Running(startLocation, constraints, description);
        assertNotNull(running);
      }
    }
  }

  @Nested
  @DisplayName("addParticipant 메소드는")
  class Describe_addParticipant {
    Running running = RunningFixtures.getDefaultRunning();

    @Nested
    @DisplayName("유저를 입력으로 받았을 때")
    class Context_with_valid_arguments {
      User participant = UserFixtures.getDefaultUser();

      @Test
      @DisplayName("참가자목록에 유저 ID를 추가한다")
      void it_adds_a_participant() {
        ReflectionTestUtils.setField(participant, "id", 1L);
        running.addParticipant(participant);
        assertTrue(running.hasParticipant(participant));
      }
    }
  }

  @Nested
  @DisplayName("hasParticipant 메소드는")
  class Describe_hasParticipant {
    Running running = RunningFixtures.getDefaultRunning();

    @Nested
    @DisplayName("유저를 입력으로 받았을때")
    class Context_with_participant {
      User participant = UserFixtures.getDefaultUser();
      @Test
      @DisplayName("유저가 있는지 확인하고 유저가 있다면 true를 반환한다")
      void it_checks_participant_exists() {
        ReflectionTestUtils.setField(participant, "id", 1L);
        running.addParticipant(participant);
        assertTrue(running.hasParticipant(participant));
      }

      @Test
      @DisplayName("유저가 있는지 확인하고 유저가 없다면 false를 반환한다")
      void it_checks_participant_non_exists() {
        ReflectionTestUtils.setField(participant, "id", 2L);
        assertFalse(running.hasParticipant(participant));
      }
    }
  }

  @Nested
  @DisplayName("removeParticipant 메소드는")
  class Describe_removeParticipant {
    Running running = RunningFixtures.getDefaultRunning();

    @Nested
    @DisplayName("유저를 입력으로 받았을때")
    class Context_with_participant {
      User participant = UserFixtures.getDefaultUser();
      @Test
      @DisplayName("참가자목록에서 해당 유저 ID를 제거한다")
      void it_removes_a_participant() {
        ReflectionTestUtils.setField(participant, "id", 1L);
        running.addParticipant(participant);
        running.removeParticipant(participant);
        assertFalse(running.hasParticipant(participant));
      }
    }
  }

  @Nested
  @DisplayName("getAllParticipantsId 메소드는")
  class Describe_getAllParticipantsId {
    Running running = RunningFixtures.getDefaultRunning();

    @Nested
    @DisplayName("참가자가 있는 경우")
    class Context_with_participants {
      User participant1 = UserFixtures.getDefaultUser();
      User participant2 = UserFixtures.getDefaultUserWithNickname("participant2");

      @Test
      @DisplayName("참가자 목록을 반환한다")
      void it_returns_participants() {
        ReflectionTestUtils.setField(participant1, "id", 1L);
        ReflectionTestUtils.setField(participant2, "id", 2L);
        running.addParticipant(participant1);
        running.addParticipant(participant2);
        assertEquals(2, running.getAllParticipantsId().size());
      }
    }

    @Nested
    @DisplayName("참가자가 없는 경우")
    class Context_without_participants {
      @Test
      @DisplayName("빈 목록을 반환한다")
      void it_returns_empty_list() {
        assertTrue(running.getAllParticipantsId().isEmpty());
      }
    }
  }
}