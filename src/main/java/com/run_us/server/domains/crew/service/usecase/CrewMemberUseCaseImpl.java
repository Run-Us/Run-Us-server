package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.response.KickMemberResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewPrincipal;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.crew.service.CrewValidator;
import com.run_us.server.domains.crew.service.resolver.CrewIdResolver;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.service.resolver.UserIdResolver;
import com.run_us.server.global.common.SuccessResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewMemberUseCaseImpl implements CrewMemberUseCase {
    private final CrewService crewService;
    private final CrewValidator crewValidator;
    private final CrewIdResolver crewIdResolver;
    private final UserIdResolver userIdResolver;

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
}
