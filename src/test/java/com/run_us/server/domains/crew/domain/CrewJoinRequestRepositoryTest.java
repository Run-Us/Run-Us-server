package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.repository.CrewJoinRequestRepository;
import com.run_us.server.domains.crew.repository.CrewRepository;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserFixtures;
import com.run_us.server.domains.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class CrewJoinRequestRepositoryTest {

  @Autowired
  private CrewJoinRequestRepository crewJoinRequestRepository;
  @Autowired
  private CrewRepository crewRepository;
  @Autowired
  private UserRepository userRepository;

  @DisplayName("크루 가입 요청 생성 테스트")
  @Test
  void test_create_join_request() {
    // given
    User user = UserFixtures.getDefaultUser();
    userRepository.saveAndFlush(user);
    Crew crew = CrewFixtures.createCrewWithOwner(user, "빵빵런", "러닝하고 빵먹고");
    crewRepository.saveAndFlush(crew);
    Integer userInternalId = 1;
    String answer = "달리기 좋아요";

    // when
    CrewJoinRequest joinRequest = crewJoinRequestRepository.saveAndFlush(CrewJoinRequest.from(userInternalId, crew.getId(), answer));

    // then
    CrewJoinRequest savedRequest = crewJoinRequestRepository.findByCrewIdAndUserId(crew.getId(), userInternalId).orElse(null);
    assertNotNull(savedRequest);
    assertEquals(joinRequest.getCrewId(), crew.getId());
    assertEquals(joinRequest.getUserId(), userInternalId);
    assertEquals(joinRequest.getAnswer(), answer);
  }

  @DisplayName("1달 이내 크루 가입 요청 조회 테스트")
  @Test
  void test_find_join_request_within_month() {
    // given
    User user = UserFixtures.getDefaultUser();
    userRepository.saveAndFlush(user);
    Crew crew = CrewFixtures.createCrewWithOwner(user, "빵빵런", "러닝하고 빵먹고");
    crewRepository.saveAndFlush(crew);
    Integer userInternalId = 1;
    String answer = "달리기 좋아요";

    CrewJoinRequest joinRequestBeforeDays = CrewJoinRequest.builder()
        .crewId(crew.getId())
        .userId(userInternalId)
        .answer(answer)
        .requestedAt(ZonedDateTime.now().minusWeeks(1))
        .processedAt(ZonedDateTime.now().minusDays(1))
        .status(CrewJoinRequestStatus.WAITING)
        .build();
    crewJoinRequestRepository.saveAndFlush(joinRequestBeforeDays);

    // when
    ZonedDateTime monthAgo = ZonedDateTime.now().minusMonths(1);
    CrewJoinRequest joinRequests = crewJoinRequestRepository.findByCrewIdAndUserIdAndAfterRequestedAt(crew.getId(), userInternalId, monthAgo).orElse(null);

    // then
    assertNotNull(joinRequests);
  }


}
