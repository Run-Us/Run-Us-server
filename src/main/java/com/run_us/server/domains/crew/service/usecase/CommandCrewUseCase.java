package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.UpdateCrewInfoRequest;
import com.run_us.server.domains.crew.controller.model.request.UpdateCrewJoinTypeRequest;
import com.run_us.server.domains.crew.controller.model.response.CloseCrewResponse;
import com.run_us.server.domains.crew.controller.model.response.UpdateCrewInfoResponse;
import com.run_us.server.domains.crew.controller.model.response.UpdateCrewJoinRuleResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface CommandCrewUseCase {
    SuccessResponse<UpdateCrewInfoResponse> updateCrewInfo(String crewPublicId, UpdateCrewInfoRequest requestDto, String userPublicId);

    SuccessResponse<UpdateCrewJoinRuleResponse> updateCrewJoinRule(String crewPublicId, UpdateCrewJoinTypeRequest requestDto, String userPublicId);

    SuccessResponse<CloseCrewResponse> closeCrew(String crewPublicId, String userPublicId);
}
