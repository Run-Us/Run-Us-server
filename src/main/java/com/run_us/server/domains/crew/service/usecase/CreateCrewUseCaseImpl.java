package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateCrewRequest;
import com.run_us.server.domains.crew.controller.model.response.CreateCrewResponse;
import com.run_us.server.domains.crew.controller.model.response.CrewHttpResponseCode;
import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.service.CrewCommandService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateCrewUseCaseImpl implements CreateCrewUseCase {

    private final UserService userService;
    private final CrewCommandService crewCommandService;

    @Override
    @Transactional
    public SuccessResponse<CreateCrewResponse> createCrew(CreateCrewRequest requestDto, String creatorId) {
        User creator = userService.getUserByPublicId(creatorId);
        Crew crew = crewCommandService.saveCrew(requestDto, creator);

        return SuccessResponse.of(CrewHttpResponseCode.CREW_CREATED, CreateCrewResponse.from(crew));
    }
}
