package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.request.UpdateCrewInfoRequest;
import com.run_us.server.domains.crew.controller.model.response.UpdateCrewInfoResponse;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.service.CommandCrewService;
import com.run_us.server.domains.crew.service.CrewService;
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

    @Override
    @Transactional
    public SuccessResponse<UpdateCrewInfoResponse> updateCrewInfo(String crewPublicId, UpdateCrewInfoRequest requestDto, Integer userId) {
        Crew crew = crewService.getCrewByPublicId(crewPublicId);
        if (!crew.isOwner(userId)) {
            throw new CrewException(CrewErrorCode.FORBIDDEN_UPDATE_CREW);
        }

        commandCrewService.updateCrewInfo(requestDto, crew);
        return SuccessResponse.of(CrewHttpResponseCode.CREW_UPDATED, UpdateCrewInfoResponse.from(crew.getPublicId()));
    }
}
