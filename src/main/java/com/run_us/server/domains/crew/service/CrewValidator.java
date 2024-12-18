package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;

import com.run_us.server.domains.crew.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrewValidator {
    private final CrewRepository crewRepository;

    public void validateCanJoinCrew(Integer userId, Crew crew) {
        if (crew.isMember(userId)) {
            throw new CrewException(CrewErrorCode.ALREADY_CREW_MEMBER);
        }

        if (crew.hasWaitingRequest(userId)) {
            throw new CrewException(CrewErrorCode.DUPLICATE_JOIN_REQUEST);
        }

        if (!crew.isActive()) {
            throw new CrewException(CrewErrorCode.INVALID_JOIN_REQUEST);
        }
    }
}