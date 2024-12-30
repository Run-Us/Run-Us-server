package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateCrewRequest;
import com.run_us.server.domains.crew.controller.model.response.CreateCrewResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface CreateCrewUseCase {
    SuccessResponse<CreateCrewResponse> createCrew(CreateCrewRequest requestDto, String creatorId);
}
