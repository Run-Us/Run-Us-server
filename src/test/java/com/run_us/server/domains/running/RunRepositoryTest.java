package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.domain.Participant;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.repository.ParticipantRepository;
import com.run_us.server.domains.running.run.repository.RunRepository;
import com.run_us.server.domains.running.run.service.model.JoinedRunPreviewResponse;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserFixtures;
import com.run_us.server.domains.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RunRepositoryTest {

  @Autowired
  private RunRepository runRepository;
  @Autowired
  private ParticipantRepository participantRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("러닝 세션 삭제 테스트")
  void test_delete_run() {
    // given
    Run run = RunFixtures.createRun();
    runRepository.save(run);
    // when
    runRepository.delete(run);
    //then
    assertNull(runRepository.findById(run.getId()).orElse(null));
  }

  @Test
  @DisplayName("러닝세션 삭제 시 참가자 정보는 유지")
  void test_delete_run_with_participants() {
    // given
    saveUsersForParticipants();
    Run run = RunFixtures.createRun();
    runRepository.save(run);
    Participant participant = ParticipantFixtures.createParticipantWithRunAndUserId(run, 1);
    Participant participant2 = ParticipantFixtures.createParticipantWithRunAndUserId(run, 2);
    participantRepository.saveAll(List.of(participant, participant2));
    // when

    runRepository.delete(run);
    // then
    assertEquals(2, participantRepository.findByRunId(run.getId()).size());
  }

  @DisplayName("사용자가 참가한 러닝 세션 조회")
  @Test
  void test_find_joined_run() {
    // given
    List<User> users = saveUsersForParticipants();

    Run run = RunFixtures.createRun();
    runRepository.saveAndFlush(run);

    List<Participant> participants = new ArrayList<>();
    users.stream().map(User::getId).forEach(userId -> {
      Participant participant = ParticipantFixtures.createParticipantWithRunAndUserId(run, userId);
      participants.add(participant);
    });
    participantRepository.saveAllAndFlush(participants);

    // when
    List<JoinedRunPreviewResponse> joinedRuns = runRepository.findJoinedRunPreviews(users.getFirst().getId(), PageRequest.of(0, 10)).getContent();
    // then
    assertEquals(1, joinedRuns.size());
    assertEquals(joinedRuns.getFirst().getParticipantCount(), 2);
  }

  private List<User> saveUsersForParticipants() {
    User user = UserFixtures.getDefaultUser();
    User user2 = UserFixtures.getDefaultUser();
    return userRepository.saveAllAndFlush(List.of(user, user2));
  }
}
