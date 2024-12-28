package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CrewJoinRequestInternalResponse;
import com.run_us.server.domains.crew.controller.model.response.FetchJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.ReviewJoinRequestResponse;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CrewJoinUseCase {
    CrewJoinRequestInternalResponse createJoinRequest(String crewPublicId, Integer userId, CreateJoinRequest request);
    CrewJoinRequestInternalResponse cancelJoinRequest(String crewPublicId, Integer userId);
    List<FetchJoinRequestResponse> getJoinRequests(String crewPublicId, PageRequest pageRequest, Integer userId);
    ReviewJoinRequestResponse reviewJoinRequest(String crewPublicId, Integer requestId, CrewJoinRequestStatus status, Integer userId);
}
