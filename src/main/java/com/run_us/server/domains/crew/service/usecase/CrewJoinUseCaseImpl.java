package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CrewJoinRequestInternalResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.crew.service.CrewValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewJoinUseCaseImpl implements CrewJoinUseCase {
    private final CrewService crewService;
    private final CrewValidator crewValidator;

    @Override
    @Transactional
    public CrewJoinRequestInternalResponse createJoinRequest(String crewPublicId, Integer userInternalId, CreateJoinRequest request) {
        log.debug("action=create_join_request_start crewPublicId={} userInternalId={}", crewPublicId, userInternalId);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewValidator.validateCanJoinCrew(userInternalId, crew);

        CrewJoinRequest joinRequest = crewService.createJoinRequest(crew, userInternalId, request.getAnswer());

        log.debug("action=create_join_request_end crewPublicId={} userInternalId={} status={}",
                crewPublicId, userInternalId, joinRequest.getStatus());

        return CrewJoinRequestInternalResponse.builder()
                .crewPublicId(crewPublicId)
                .userInternalId(userInternalId)
                .status(joinRequest.getStatus())
                .requestedAt(joinRequest.getRequestedAt())
                .build();
    }
}