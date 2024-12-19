package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.repository.CrewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewService {
    private final CrewRepository crewRepository;

    public Crew getCrewByPublicId(String crewPublicId) {
        log.debug("action=fetch_crew crewPublicId={}", crewPublicId);
        return crewRepository.findByPublicId(crewPublicId)
                .orElseThrow(() -> new CrewException(CrewErrorCode.CREW_NOT_FOUND));
    }

    @Transactional
    public CrewJoinRequest createJoinRequest(Crew crew, Integer userInternalId, String answer) {
        log.debug("action=create_join_request crewPublicId={} userInternalId={}", crew.getPublicId(), userInternalId);

        CrewJoinRequest joinRequest = CrewJoinRequest.from(userInternalId, answer);

        if (crew.getJoinType() == CrewJoinType.OPEN) {
            log.debug("action=auto_approve_join_request crewPublicId={} userInternalId={}", crew.getPublicId(), userInternalId);
            joinRequest.approve(crew.getOwner());
            crew.addMember(userInternalId);
        }

        crew.addJoinRequest(joinRequest);
        crewRepository.save(crew);

        log.debug("action=create_join_request_complete crewPublicId={} userInternalId={} status={}",
                crew.getPublicId(), userInternalId, joinRequest.getStatus());

        return joinRequest;
    }
}