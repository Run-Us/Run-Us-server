package com.run_us.server.domains.crew.service.usecase;
import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.response.GetCrewHomeResponse;
import com.run_us.server.domains.crew.controller.model.response.RunCardInfo;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.crew.service.CrewValidator;
import com.run_us.server.domains.crew.service.model.CrewRunCardInfo;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.running.run.service.model.GetCrewCardRun;
import com.run_us.server.domains.running.run.service.strategy.RunTopMessageContext;
import com.run_us.server.domains.running.run.service.strategy.TimePlaceMessage;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.domains.user.service.resolver.UserIdResolver;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCrewUseCaseImpl implements QueryCrewUseCase {
    private final CrewService crewService;
    private final RunQueryService runQueryService;
    private final UserService userService;
    private final UserIdResolver userIdResolver;
    private final CrewValidator crewValidator;

    @Override
    public SuccessResponse<GetCrewHomeResponse> getCrewHome(String crewPublicId, String userPublicId) {
        UserPrincipal userPrincipal = userIdResolver.resolve(userPublicId);
        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        crewValidator.validateCrewMember(crew, userPrincipal.getInternalId());

        boolean existNewJoinRequest = crewService.existNewJoinRequest(crew.getId());

        CrewRunCardInfo crewRunCardInfo = getCrewRunCardInfo(crew);

        return SuccessResponse.of(CrewHttpResponseCode.CREW_HOME_FETCHED, GetCrewHomeResponse.from(crew, existNewJoinRequest, crewRunCardInfo));
    }

    private CrewRunCardInfo getCrewRunCardInfo(Crew crew) {
        GetCrewCardRun crewCardRun = runQueryService.getCrewCardRun(crew.getId());

        CrewRunCardInfo.CrewRunCardInfoBuilder crewCardRunBuilder = CrewRunCardInfo.builder();

        Run regularRun = crewCardRun.getRegularRun();
        if (regularRun != null) {
            RunCardInfo regularRunCard = getRunCardInfo(regularRun);
            crewCardRunBuilder.regularRunCard(regularRunCard);
        }

        Run irregularRun = crewCardRun.getIrregularRun();
        if(irregularRun != null) {
            RunCardInfo irregularRunCard = getRunCardInfo(irregularRun);
            crewCardRunBuilder.irregularRunCard(irregularRunCard);
        }

        return crewCardRunBuilder.build();
    }

    private RunCardInfo getRunCardInfo(Run run) {
        User runHost = userService.getUserByInternalId(run.getHostId());
        return RunCardInfo.from(run, RunTopMessageContext.getMessage(new TimePlaceMessage(run)), runHost);
    }

}