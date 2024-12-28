package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CrewJoinRequestInternalResponse;
import com.run_us.server.domains.crew.controller.model.response.FetchJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.ReviewJoinRequestResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.crew.service.CrewValidator;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewJoinUseCaseImpl implements CrewJoinUseCase {
    private final CrewService crewService;
    private final CrewValidator crewValidator;
    private final UserService userService;

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

    @Override
    @Transactional
    public CrewJoinRequestInternalResponse cancelJoinRequest(String crewPublicId, Integer userInternalId) {
        log.debug("action=cancel_join_request_start crewPublicId={} userInternalId={}", crewPublicId, userInternalId);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewService.cancelJoinRequest(crew, userInternalId);

        log.debug("action=cancel_join_request_end crewPublicId={} userInternalId={}", crewPublicId, userInternalId);

        return CrewJoinRequestInternalResponse.builder()
                .crewPublicId(crewPublicId)
                .userInternalId(userInternalId)
                .status(null)
                .requestedAt(null)
                .build();
    }

    @Override
    @Transactional
    public List<FetchJoinRequestResponse> getJoinRequests(String crewPublicId, PageRequest pageRequest, Integer userInternalId) {
        log.debug("action=get_join_requests crewPublicId={} userInternalId={}", crewPublicId, userInternalId);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewValidator.validateCanFetchJoinRequests(userInternalId, crew);

        List<CrewJoinRequest> joinRequests = crewService.getJoinRequests(crew, pageRequest);

        List<Integer> userIds = joinRequests.stream()
                .map(CrewJoinRequest::getUserId)
                .collect(Collectors.toList());
        Map<Integer, User> userMap = userService.getUserMapByIds(userIds);

        return joinRequests.stream()
                .map(request -> {
                    User userInfo = userMap.get(request.getUserId());
                    return new FetchJoinRequestResponse(
                            request.getId(),
                            userInfo.getPublicId(),
                            userInfo.getProfile().getNickname(),
                            userInfo.getProfile().getImgUrl(),
                            request.getAnswer()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewJoinRequestResponse reviewJoinRequest(String crewPublicId, Integer requestId, CrewJoinRequestStatus status, Integer userInternalId) {
        log.debug("action=review_join_request_start crewPublicId={} requestId={} status={}",
                crewPublicId, requestId, status);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewValidator.validateCanReviewJoinRequest(userInternalId, requestId, status, crew);

        CrewJoinRequest joinRequest = crewService.reviewJoinRequest(
                crew,
                requestId,
                status,
                userInternalId
        );

        log.debug("action=review_join_request_end crewPublicId={} requestId={}", crewPublicId, requestId);

        return new ReviewJoinRequestResponse(
                joinRequest.getId()
        );
    }
}