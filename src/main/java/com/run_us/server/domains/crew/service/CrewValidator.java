package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumSet;

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
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
    }

    public void validateCanFetchJoinRequests(Integer userId, Crew crew) {
        if(!crew.getOwner().getId().equals(userId)) {
            throw new CrewException(CrewErrorCode.CREW_NOT_FOUND);
        }

        if(!crew.isActive()) {
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
    }

    public void validateCanReviewJoinRequest(Integer userId, Integer requestId, CrewJoinRequestStatus status, Crew crew) {
        if(!crew.getOwner().getId().equals(userId)) {
            throw new CrewException(CrewErrorCode.CREW_NOT_FOUND);
        }

        if(!crewRepository.existsWaitingJoinRequest(crew.getId(), requestId)) {
            throw new CrewException(CrewErrorCode.JOIN_REQUEST_NOT_FOUND);
        }

        if(!EnumSet.of(CrewJoinRequestStatus.APPROVED, CrewJoinRequestStatus.REJECTED).contains(status)) {
            throw new CrewException(CrewErrorCode.INVALID_JOIN_REQUEST_STATUS);
        }

        if(!crew.isActive()) {
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
    }
}