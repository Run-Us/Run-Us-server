package com.run_us.server.domains.crew.service;

import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.repository.CrewJoinRequestRepository;
import com.run_us.server.domains.crew.repository.CrewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewService {
    private final CrewRepository crewRepository;
    private final CrewJoinRequestRepository crewJoinRequestRepository;

    public Crew getCrewByPublicId(String crewPublicId) {
        log.debug("action=fetch_crew crewPublicId={}", crewPublicId);
        return crewRepository.findByPublicId(crewPublicId)
                .orElseThrow(() -> new CrewException(CrewErrorCode.CREW_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<CrewJoinRequest> getJoinRequests(Crew crew, PageRequest pageRequest) {
        log.debug("action=get_join_requests_start crewId={}", crew.getPublicId());
        return crewJoinRequestRepository.findAllByCrewId(crew.getId(), pageRequest).getContent();
    }

    @Transactional
    public CrewJoinRequest createJoinRequest(Crew crew, Integer userInternalId, String answer) {
        log.debug("action=create_join_request crewPublicId={} userInternalId={}", crew.getPublicId(), userInternalId);

        CrewJoinRequest joinRequest = CrewJoinRequest.from(userInternalId, crew.getId(), answer);

        if (crew.getJoinType() == CrewJoinType.OPEN) {
            log.debug("action=auto_approve_join_request crewPublicId={} userInternalId={}", crew.getPublicId(), userInternalId);
            joinRequest.review(crew.getOwner().getId(), CrewJoinRequestStatus.APPROVED);
            crew.addMember(userInternalId);
        }
        log.debug("action=create_join_request_complete crewPublicId={} userInternalId={} status={}",
                crew.getPublicId(), userInternalId, joinRequest.getStatus());
        return joinRequest;
    }

    @Transactional
    public void cancelJoinRequest(Crew crew, Integer userInternalId) {
        log.debug("action=cancel_join_request crewPublicId={} userInternalId={}", crew.getPublicId(), userInternalId);
        CrewJoinRequest joinRequest = crewJoinRequestRepository.findByCrewIdAndUserId(crew.getId(), userInternalId)
                .orElseThrow(() -> new CrewException(CrewErrorCode.JOIN_REQUEST_NOT_FOUND));
        joinRequest.cancel();
        log.debug("action=cancel_join_request_complete crewPublicId={} userInternalId={}", crew.getPublicId(), userInternalId);
    }

    @Transactional
    public CrewJoinRequest reviewJoinRequest(Crew crew, Integer requestId, CrewJoinRequestStatus status, Integer userInternalId) {
        log.debug("action=review_join_request crewPublicId={} requestId={} status={} userInternalId={}",
                crew.getPublicId(), requestId, status, userInternalId);

        CrewJoinRequest request = crewJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new CrewException(CrewErrorCode.JOIN_REQUEST_NOT_FOUND));

        request.review(userInternalId, status);
        if(status == CrewJoinRequestStatus.APPROVED) {
            crew.addMember(request.getUserId());
        }
        log.debug("action=process_join_request_usecase_complete crewPublicId={} requestId={}",
                crew.getPublicId(), requestId);
        return request;
    }
}