package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CrewJoinRequestInternalResponse;

public interface CrewJoinUseCase {
    CrewJoinRequestInternalResponse createJoinRequest(String crewPublicId, Integer userId, CreateJoinRequest request);
}
