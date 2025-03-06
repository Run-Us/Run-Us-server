package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.request.UpdateCrewInfoRequest;
import com.run_us.server.domains.crew.controller.model.request.UpdateCrewJoinTypeRequest;
import com.run_us.server.domains.crew.controller.model.response.CloseCrewResponse;
import com.run_us.server.domains.crew.controller.model.response.UpdateCrewInfoResponse;
import com.run_us.server.domains.crew.controller.model.response.UpdateCrewJoinRuleResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.service.CommandCrewService;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.service.resolver.UserIdResolver;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommandCrewUseCaseImpl implements CommandCrewUseCase {
    private final CrewService crewService;
    private final CommandCrewService commandCrewService;
    private final UserIdResolver userIdResolver;

    @Override
    @Transactional
    public SuccessResponse<UpdateCrewInfoResponse> updateCrewInfo(String crewPublicId, UpdateCrewInfoRequest requestDto, String userPublicId) {
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        commandCrewService.updateCrewInfo(requestDto, crew, userPrincipal.getInternalId());
        return SuccessResponse.of(CrewHttpResponseCode.CREW_UPDATED, UpdateCrewInfoResponse.from(crew.getPublicId()));
    }

    @Override
    @Transactional
    public SuccessResponse<UpdateCrewJoinRuleResponse> updateCrewJoinRule(String crewPublicId, UpdateCrewJoinTypeRequest requestDto, String userPublicId) {
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        commandCrewService.updateCrewJoinRule(requestDto, crew, userPrincipal.getInternalId());
        return SuccessResponse.of(CrewHttpResponseCode.CREW_JOIN_RULE_UPDATED, UpdateCrewJoinRuleResponse.from(crew));
    }

    @Override
    @Transactional
    public SuccessResponse<CloseCrewResponse> closeCrew(String crewPublicId, String userPublicId) {
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);

        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        commandCrewService.closeCrew(crew, userPrincipal.getInternalId());
        return SuccessResponse.of(CrewHttpResponseCode.CREW_CLOSE_SUCCESS, CloseCrewResponse.from(crew));
    }
}
