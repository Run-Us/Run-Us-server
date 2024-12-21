package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;

import com.run_us.server.domains.crew.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CrewValidator {
    private final CrewRepository crewRepository;
    private static final int REJECT_LOCK_PERIOD_MONTHS = 1;

    public void validateCanJoinCrew(Integer userId, Crew crew) {
        if (crewRepository.existsMembershipByCrewIdAndUserId(crew.getId(), userId)) {
            throw new CrewException(CrewErrorCode.ALREADY_CREW_MEMBER);
        }

        if (crewRepository.existsWaitingRequestByCrewIdAndUserId(crew.getId(), userId)) {
            throw new CrewException(CrewErrorCode.DUPLICATE_JOIN_REQUEST);
        }

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(REJECT_LOCK_PERIOD_MONTHS);
        if (crewRepository.existsRecentRejectedRequestByCrewIdAndUserId(
                crew.getId(), userId, oneMonthAgo)) {
            throw new CrewException(CrewErrorCode.RECENTLY_REJECTED_REQUEST);
        }

        if (!crew.isActive()) {
            throw new CrewException(CrewErrorCode.INVALID_JOIN_REQUEST);
        }
    }
}