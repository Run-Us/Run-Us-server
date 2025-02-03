package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.response.FetchMemberResponse;
import com.run_us.server.domains.crew.controller.model.response.KickMemberResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewMembership;
import com.run_us.server.domains.crew.domain.CrewPrincipal;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.crew.service.CrewValidator;
import com.run_us.server.domains.crew.service.resolver.CrewIdResolver;
import com.run_us.server.domains.running.record.service.RecordQueryService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.domains.user.service.resolver.UserIdResolver;
import com.run_us.server.global.common.SuccessResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewMemberUseCaseImpl implements CrewMemberUseCase {
    private final CrewService crewService;
    private final CrewValidator crewValidator;
    private final CrewIdResolver crewIdResolver;
    private final UserIdResolver userIdResolver;
    private final UserService userService;

    @Override
    @Transactional
    public SuccessResponse<KickMemberResponse> kickMember(
        String crewPublicId, String actionUserPublicId, String targetMemberPublicId) {
        CrewPrincipal crewPrincipal = crewIdResolver.resolve(crewPublicId);
        UserPrincipal actionUserPrincipal = userIdResolver.resolve(actionUserPublicId);
        UserPrincipal targetMemberPrincipal = userIdResolver.resolve(targetMemberPublicId);

        log.info(
            "action=kick_member_start crewPublicId={} actionUserPublicId={} targetMemberPublicId={}",
            crewPrincipal.getPublicId(), actionUserPrincipal.getPublicId(),
            targetMemberPrincipal.getPublicId());

        Crew crew = crewService.getCrewByPublicId(crewPrincipal.getPublicId());
        crewValidator.validateCanKickMember(
            actionUserPrincipal.getInternalId(), targetMemberPrincipal.getInternalId(), crew);
        crewService.removeMember(crew, targetMemberPrincipal.getInternalId());

        log.info(
            "action=kick_member_end crewPublicId={} actionUserPublicId={} targetMemberPublicId={}",
            crewPrincipal.getPublicId(), actionUserPrincipal.getPublicId(),
            targetMemberPrincipal.getPublicId());

        return SuccessResponse.of(
            CrewHttpResponseCode.KICK_MEMBER_SUCCESS,
            KickMemberResponse.builder()
                .userPublicId(targetMemberPrincipal.getPublicId())
                .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SuccessResponse<List<FetchMemberResponse>> getMembers(
        String crewPublicId, String userPublicId, PageRequest pageRequest){
        CrewPrincipal crewPrincipal = crewIdResolver.resolve(crewPublicId);
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        log.info("action=get_members_start crewPublicId={} userPublicId={} page={} size={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId(),
            pageRequest.getPageNumber(), pageRequest.getPageSize());

        Crew crew = crewService.getCrewByPublicId(crewPrincipal.getPublicId());
        crewValidator.validateCanFetchMembers(userPrincipal.getInternalId(), crew);

        List<FetchMemberResponse> memberResponses = getMemberResponses(crew, pageRequest);

        log.info("action=get_members_end crewPublicId={} userPublicId={}",
            crewPrincipal.getPublicId(), userPrincipal.getPublicId());

        return SuccessResponse.of(
            CrewHttpResponseCode.GET_MEMBERS_SUCCESS,
            memberResponses
        );
    }

    private List<FetchMemberResponse> getMemberResponses(
        Crew crew,
        PageRequest pageRequest
    ) {
        List<CrewMembership> memberships = crewService.getMemberships(crew, pageRequest);

        if (memberships.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> userIds = getUserIds(memberships);

        Map<Integer, User> userMap = userService.getUserMapByIds(userIds);
        Map<Integer, Integer> distanceMap = getUserTotalDistance(userMap);

        return mapToMemberResponses(memberships, userMap, distanceMap);
    }

    private List<Integer> getUserIds(List<CrewMembership> memberships) {
        return memberships.stream()
            .map(CrewMembership::getUserId)
            .toList();
    }

    private Map<Integer, Integer> getUserTotalDistance(Map<Integer, User> userMap) {
        return userMap.entrySet().parallelStream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> getDistanceOrDefault(entry.getValue())
            ));
    }

    private int getDistanceOrDefault(User user) {
        if (user == null || user.getProfile() == null) {
            return 0;
        }
        Integer distance = user.getProfile().getTotalDistance();
        return distance != null ? distance : 0;
    }

    private List<FetchMemberResponse> mapToMemberResponses(
        List<CrewMembership> memberships,
        Map<Integer, User> userMap,
        Map<Integer, Integer> distanceMap
    ) {
        return memberships.stream()
            .map(membership -> createMemberResponse(membership, userMap, distanceMap))
            .toList();
    }

    private FetchMemberResponse createMemberResponse(
        CrewMembership membership,
        Map<Integer, User> userMap,
        Map<Integer, Integer> distanceMap
    ) {
        Integer userId = membership.getUserId();
        return FetchMemberResponse.from(
            userMap.get(userId),
            membership,
            distanceMap.getOrDefault(userId, 0)
        );
    }
}
