package com.run_us.server.domains.running;

import com.run_us.server.config.TestRedisConfiguration;
import com.run_us.server.domains.running.domain.record.PersonalRecord;
import com.run_us.server.domains.running.repository.PersonalRecordRepository;
import com.run_us.server.domains.running.service.model.JoinedParticipant;
import com.run_us.server.domains.running.controller.RunningController;
import com.run_us.server.domains.running.domain.running.Running;
import com.run_us.server.domains.running.repository.RunningRepository;
import com.run_us.server.domains.running.service.model.PersonalRecordQueryResult;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserFixtures;
import com.run_us.server.domains.user.repository.UserRepository;
import com.run_us.server.global.common.SuccessResponse;
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
  private UserRepository userRepository;

  @Autowired
  private RunningRepository runningRepository;

  @Autowired
    private PersonalRecordRepository personalRecordRepository;

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
      SuccessResponse successResponse = runningController.joinedParticipants(runningId);
      List<JoinedParticipant> joinedParticipants = (List<JoinedParticipant>) successResponse.getPayload();
      //then
      Assertions.assertEquals(2, joinedParticipants.size());
    }
  }

  @Nested
  @DisplayName("getPersonalRecord 메소드는")
    class Describe_getPersonalRecord {

        private Running r1;
        private User u1;

        @BeforeEach
        void setUp() {
          // save users
          u1 = UserFixtures.getDefaultUserWithNickname("us1");
          userRepository.saveAndFlush(u1);
          // create running
          r1 = RunningFixtures.getDefaultRunningWithParticipants(List.of(u1));
          runningRepository.saveAndFlush(r1);

          PersonalRecord personalRecord = PersonalRecordFixtures.getPersonalRecordWithUserIdAndRunningId(u1.getId(), r1.getId());
          personalRecordRepository.saveAndFlush(personalRecord);
        }

        @DisplayName("개인 기록을 반환한다")
        @Test
        void it_returns_personal_record() {
          // given
          String runningId = r1.getPublicKey();
          String userId = u1.getPublicId();
          // when
          SuccessResponse<PersonalRecordQueryResult> successResponse = runningController.getPersonalRecord(runningId, userId);
          PersonalRecordQueryResult personalRecord = (PersonalRecordQueryResult) successResponse.getPayload();
          //then
          Assertions.assertNotNull(successResponse.getPayload());
          Assertions.assertEquals(personalRecord.getAveragePaceInMilliseconds(), 1000);
          Assertions.assertEquals(personalRecord.getRunningDistanceInMeters(), 1000);
          Assertions.assertEquals(personalRecord.getRunningDurationInMilliseconds(), 1000);
        }
    }
}
