package com.run_us.server.domains.crew.service.usecase;

import com.run_us.server.domains.crew.controller.model.response.GetCrewHomeResponse;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCrewUseCaseImpl implements QueryCrewUseCase {
    public final CrewService crewService;

    @Override
    public SuccessResponse<GetCrewHomeResponse> getCrewHome(String crewPublicId, Integer internalId) {
        // TODO : 작업 전
        return null;
    }
}
