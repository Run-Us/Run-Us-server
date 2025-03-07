package com.run_us.server.domains.crew.controller;

import com.run_us.server.domains.crew.controller.model.response.GetCrewHomeResponse;
import com.run_us.server.domains.crew.service.usecase.QueryCrewUseCase;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.security.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/crews")
@RequiredArgsConstructor
public class QueryCrewController {
    private final QueryCrewUseCase queryCrewUseCase;

    @GetMapping("/{crewPublicId}/home")
    public ResponseEntity<SuccessResponse<GetCrewHomeResponse>> getCrewHome(
            @PathVariable String crewPublicId,
            @CurrentUser String currentUserPublicId) {
        log.info("action=get_crew_home crew_public_id={}, user_id={}", crewPublicId, currentUserPublicId);

        SuccessResponse<GetCrewHomeResponse> response = queryCrewUseCase.getCrewHome(crewPublicId, currentUserPublicId);
        return ResponseEntity.ok().body(response);
    }
}
