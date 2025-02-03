package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CancelJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.CreateJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.FetchJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.ReviewJoinRequestResponse;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.global.common.SuccessResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CrewJoinUseCase {
    SuccessResponse<CreateJoinRequestResponse> createJoinRequest(String crewPublicId, String userPublicId, CreateJoinRequest request);
    SuccessResponse<CancelJoinRequestResponse> cancelJoinRequest(String crewPublicId, String userPublicId);
    SuccessResponse<List<FetchJoinRequestResponse>> getJoinRequests(String crewPublicId, PageRequest pageRequest, String userPublicId);
    SuccessResponse<ReviewJoinRequestResponse> reviewJoinRequest(String crewPublicId, Integer requestId, CrewJoinRequestStatus status, String userPublicId);
}
