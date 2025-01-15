package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.response.KickMemberResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface CrewMemberUseCase {
    SuccessResponse<KickMemberResponse> kickMember(String crewPublicId, String userPublicId, String memberPublicId);
}
