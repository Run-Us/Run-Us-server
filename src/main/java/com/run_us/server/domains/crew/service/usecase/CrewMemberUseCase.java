package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.response.FetchMemberResponse;
import com.run_us.server.domains.crew.controller.model.response.KickMemberResponse;
import com.run_us.server.global.common.SuccessResponse;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface CrewMemberUseCase {
    SuccessResponse<KickMemberResponse> kickMember(String crewPublicId, String userPublicId, String memberPublicId);
    SuccessResponse<List<FetchMemberResponse>> getMembers(String crewPublicId, String userPublicId, PageRequest pageRequest);
}
