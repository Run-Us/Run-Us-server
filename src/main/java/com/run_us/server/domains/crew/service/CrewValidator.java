package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;

import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.repository.CrewJoinRequestRepository;
import com.run_us.server.domains.crew.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.EnumSet;

import static com.run_us.server.global.common.GlobalConst.ZONE_ID;

@Component
@RequiredArgsConstructor
public class CrewValidator {
    private final CrewRepository crewRepository;
    private final CrewJoinRequestRepository crewJoinRequestRepository;
    private static final int REJECT_LOCK_PERIOD_MONTHS = 1;

    public void validateCanJoinCrew(Integer userId, Crew crew) {
        if (!crew.isActive()) {
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
        if (crewRepository.existsMembershipByCrewIdAndUserId(crew.getId(), userId)) {
            throw new CrewException(CrewErrorCode.ALREADY_CREW_MEMBER);
        }
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(REJECT_LOCK_PERIOD_MONTHS);
        CrewJoinRequest joinRequestWithinMonth = crewJoinRequestRepository.
            findByCrewIdAndUserIdAndAfterRequestedAt(crew.getId(), userId, ZonedDateTime.of(oneMonthAgo, ZONE_ID)).orElse(null);

        if(joinRequestWithinMonth == null) return;

        if(joinRequestWithinMonth.getStatus() == CrewJoinRequestStatus.WAITING) {
            throw new CrewException(CrewErrorCode.DUPLICATE_JOIN_REQUEST);
        }
        if(joinRequestWithinMonth.getStatus() == CrewJoinRequestStatus.REJECTED) {
            throw new CrewException(CrewErrorCode.RECENTLY_REJECTED_REQUEST);
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

    public void validateCanReviewJoinRequest(Integer userId, CrewJoinRequestStatus status, Crew crew) {
        if(!crew.getOwner().getId().equals(userId)) {
            throw new CrewException(CrewErrorCode.CREW_NOT_FOUND);
        }

        if(EnumSet.of(CrewJoinRequestStatus.APPROVED, CrewJoinRequestStatus.REJECTED).contains(status)) {
            throw new CrewException(CrewErrorCode.INVALID_JOIN_REQUEST_STATUS);
        }

        if(!crew.isActive()) {
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
    }

    public void validateCanKickMember(Integer userId, Integer targetMemberId, Crew crew) {
        if(!crew.getOwner().getId().equals(userId)) {
            throw new CrewException(CrewErrorCode.CREW_NOT_FOUND);
        }

        if (!crewRepository.existsMembershipByCrewIdAndUserId(crew.getId(), targetMemberId)) {
            throw new CrewException(CrewErrorCode.NOT_CREW_MEMBER);
        }

        if(!crew.isActive()) {
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
    }

    public void validateCanFetchMembers(Integer userId, Crew crew) {
        if(!crew.getOwner().getId().equals(userId)) {
            throw new CrewException(CrewErrorCode.CREW_NOT_FOUND);
        }

        if(!crew.isActive()) {
            throw new CrewException(CrewErrorCode.SUSPENDED_CREW);
        }
    }
}