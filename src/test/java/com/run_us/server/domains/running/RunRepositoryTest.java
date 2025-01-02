package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.domain.SessionAccessLevel;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
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
    List<User> users = saveUsersForParticipants();
    Run run = RunFixtures.createRun();
    runRepository.saveAndFlush(run);
    List<Participant> participants = addParticipantsToRun(users, run);
    // when
    runRepository.delete(run);
    // then
    assertEquals(participants.size(), participantRepository.findByRunId(run.getId()).size());
  }

  @DisplayName("사용자가 참가한 러닝 세션 조회")
  @Test
  void test_find_joined_run() {
    // given
    List<User> users = saveUsersForParticipants();
    Run run = RunFixtures.createRun();
    runRepository.saveAndFlush(run);
    List<Participant> participants = addParticipantsToRun(users, run);
    // when
    List<JoinedRunPreviewResponse> joinedRuns = runRepository.findJoinedRunPreviews(
        users.getFirst().getId(),
        PageRequest.of(0, 10)).getContent();
    // then
    assertEquals(1, joinedRuns.size());
    assertEquals(joinedRuns.getFirst().getParticipantCount(), participants.size());
  }

  @DisplayName("크루 러닝 세션 조회")
  @Test
  void test_crew_run_save() {
    // given
    User user = UserFixtures.getDefaultUser();
    userRepository.saveAndFlush(user);
    // when
    Integer crewId = 3;
    Run run = new Run(user.getId(), crewId);
    runRepository.saveAndFlush(run);

    // then
    List<Run> runs = runRepository.findAllByCrewId(crewId, PageRequest.of(0, 10)).getContent();
    assertEquals(1, runs.size());
    assertEquals(crewId, runs.getFirst().getCrewId());
  }

  @Test
  void test_crew_run_with_accessLevel() {
    User user = UserFixtures.getDefaultUser();
    userRepository.saveAndFlush(user);

    Integer crewId = 3;
    Run crewOnlyRun = RunFixtures.createRunWithCrewIdAndAccessLevel(crewId, SessionAccessLevel.ONLY_CREW);
    Run publicRun =  RunFixtures.createRunWithCrewIdAndAccessLevel(crewId, SessionAccessLevel.ALLOW_ALL);

    runRepository.saveAllAndFlush(List.of(crewOnlyRun, publicRun));

    List<Run> publicRuns = runRepository.findAllByCrewIdAndAccessLevel(crewId, SessionAccessLevel.ALLOW_ALL, PageRequest.of(0, 10)).getContent();
    List<Run> crewRuns = runRepository.findAllByCrewId(crewId, PageRequest.of(0, 10)).getContent();
    assertEquals(2, crewRuns.size());
    assertEquals(1, publicRuns.size());
  }

  private List<User> saveUsersForParticipants() {
    User user = UserFixtures.getDefaultUser();
    User user2 = UserFixtures.getDefaultUser();
    return userRepository.saveAllAndFlush(List.of(user, user2));
  }

  private List<Participant> addParticipantsToRun(List<User> users, Run run) {
    List<Participant> participants = new ArrayList<>();
    users.stream().map(User::getId).forEach(userId -> {
      Participant participant = ParticipantFixtures.createParticipantWithRunAndUserId(run, userId);
      participants.add(participant);
    });
    return participantRepository.saveAllAndFlush(participants);
  }
}
