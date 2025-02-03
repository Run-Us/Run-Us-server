package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CancelJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.CreateJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.FetchJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.ReviewJoinRequestResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.crew.domain.CrewPrincipal;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.crew.service.CrewValidator;
import com.run_us.server.domains.crew.service.resolver.CrewIdResolver;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.domains.user.service.resolver.UserIdResolver;
import com.run_us.server.global.common.SuccessResponse;
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
    private final CrewIdResolver crewIdResolver;
    private final UserIdResolver userIdResolver;

    @Override
    @Transactional
    public SuccessResponse<CreateJoinRequestResponse> createJoinRequest(
        String crewPublicId, String userPublicId, CreateJoinRequest request) {
        CrewPrincipal crewPrincipal = crewIdResolver.resolve(crewPublicId);
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        log.info("action=create_join_request_start crewPublicId={} userPublicId={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId());

        Crew crew = crewService.getCrewByPublicId(crewPrincipal.getPublicId());
        crewValidator.validateCanJoinCrew(userPrincipal.getInternalId(), crew);

        CrewJoinRequest joinRequest = crewService.createJoinRequest(crew,
            userPrincipal.getInternalId(), request.getAnswer());

        log.info("action=create_join_request_end crewPublicId={} userPublicId={} status={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId(), joinRequest.getStatus());

        return SuccessResponse.of(
            CrewHttpResponseCode.JOIN_REQUEST_CREATED,
            CreateJoinRequestResponse.builder()
                .crewPublicId(crewPrincipal.getPublicId())
                .userPublicId(userPrincipal.getPublicId())
                .status(joinRequest.getStatus())
                .requestedAt(joinRequest.getRequestedAt())
                .requestId(joinRequest.getId())
                .build()
        );
    }

    @Override
    @Transactional
    public SuccessResponse<CancelJoinRequestResponse> cancelJoinRequest(
        String crewPublicId, String userPublicId) {
        CrewPrincipal crewPrincipal = crewIdResolver.resolve(crewPublicId);
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        log.info("action=cancel_join_request_start crewPublicId={} userPublicId={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId());

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewService.cancelJoinRequest(crew, userPrincipal.getInternalId());

        log.info("action=cancel_join_request_end crewPublicId={} userPublicId={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId());

        return SuccessResponse.of(
            CrewHttpResponseCode.JOIN_REQUEST_CANCELLED,
            CancelJoinRequestResponse.builder()
                .requestId(null)
                .build()
        );
    }

    @Override
    @Transactional
    public SuccessResponse<List<FetchJoinRequestResponse>> getJoinRequests(
        String crewPublicId, PageRequest pageRequest, String userPublicId) {
        CrewPrincipal crewPrincipal = crewIdResolver.resolve(crewPublicId);
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        log.info("action=get_join_requests crewPublicId={} userPublicId={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId());

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewValidator.validateCanFetchJoinRequests(userPrincipal.getInternalId(), crew);

        List<CrewJoinRequest> joinRequests = crewService.getJoinRequests(crew, pageRequest);

        List<Integer> userIds = joinRequests.stream()
            .map(CrewJoinRequest::getUserId)
            .collect(Collectors.toList());
        Map<Integer, User> userMap = userService.getUserMapByIds(userIds);

        return SuccessResponse.of(
            CrewHttpResponseCode.JOIN_REQUEST_FETCHED,
            joinRequests.stream()
                .map(
                    request -> FetchJoinRequestResponse.from(request, userMap.get(request.getUserId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public SuccessResponse<ReviewJoinRequestResponse> reviewJoinRequest(
        String crewPublicId, Integer requestId, CrewJoinRequestStatus status, String userPublicId) {
        CrewPrincipal crewPrincipal = crewIdResolver.resolve(crewPublicId);
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        log.info("action=review_join_request_start crewPublicId={} requestId={} status={}",
            crewPrincipal.getPublicId(), requestId, status);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewValidator.validateCanReviewJoinRequest(userPrincipal.getInternalId(), status, crew);

        CrewJoinRequest joinRequest = crewService.reviewJoinRequest(
            crew,
            requestId,
            status,
            userPrincipal.getInternalId()
        );

        log.info("action=review_join_request_end crewPublicId={} requestId={}",
            crewPrincipal.getPublicId(), requestId);

        return SuccessResponse.of(
            CrewHttpResponseCode.JOIN_REQUEST_REVIEWED,
            ReviewJoinRequestResponse.builder()
                .requestId(joinRequest.getId())
                .build()
        ) ;
    }
}