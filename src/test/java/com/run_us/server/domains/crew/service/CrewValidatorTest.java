package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewFixtures;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.repository.CrewJoinRequestRepository;
import com.run_us.server.domains.crew.repository.CrewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CrewValidatorTest {

  @Mock
  private CrewRepository crewRepository;
  @Mock
  private CrewJoinRequestRepository crewJoinRequestRepository;

  @InjectMocks
  private CrewValidator crewValidator;

  @DisplayName("한달 이내에 제출한 가입신청이 아직 처리되지 않은 경우 에러 반환")
  @Test
  void should_fail_when_request_within_one_month_not_reviewed() {
    // given
    int userId = 1;
    Crew crew = CrewFixtures.createInitalCrew("owner", "title", "intro");
    when(crewJoinRequestRepository.findByCrewIdAndUserIdAndAfterRequestedAt(any(), any(), any())).thenReturn(
        Optional.ofNullable(CrewJoinRequest.builder()
            .crewId(crew.getId())
            .userId(userId)
            .status(CrewJoinRequestStatus.WAITING)
            .requestedAt(ZonedDateTime.now().minusMonths(1))
            .processedAt(null)
            .build())
    );
    when(crewRepository.existsMembershipByCrewIdAndUserId(any(), any())).thenReturn(false);
    assertThrows(CrewException.class, () -> crewValidator.validateCanJoinCrew(userId, crew));
  }

  @DisplayName("한달 이내에 제출한 가입신청이 거절된 경우 에러 반환")
  @Test
  void should_fail_when_request_within_one_month_rejected() {
    // given
    int userId = 1;
    Crew crew = CrewFixtures.createInitalCrew("owner", "title", "intro");
    when(crewJoinRequestRepository.findByCrewIdAndUserIdAndAfterRequestedAt(any(), any(), any())).thenReturn(
        Optional.ofNullable(CrewJoinRequest.builder()
            .crewId(crew.getId())
            .userId(userId)
            .status(CrewJoinRequestStatus.REJECTED)
            .requestedAt(ZonedDateTime.now().minusMonths(1))
            .processedAt(ZonedDateTime.now())
            .build())
    );
    when(crewRepository.existsMembershipByCrewIdAndUserId(any(), any())).thenReturn(false);

    assertThrows(CrewException.class, () -> crewValidator.validateCanJoinCrew(userId, crew));
  }
}