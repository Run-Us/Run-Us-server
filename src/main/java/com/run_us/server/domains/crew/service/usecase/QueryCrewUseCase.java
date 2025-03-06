package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.response.GetCrewHomeResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface QueryCrewUseCase {
    SuccessResponse<GetCrewHomeResponse> getCrewHome(String crewPublicId, String userPublicId);
}
