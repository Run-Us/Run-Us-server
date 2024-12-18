package com.run_us.server.domains.crew.controller;

import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.CreateJoinRequestResponse;
import com.run_us.server.domains.crew.controller.model.response.CrewJoinRequestInternalResponse;
import com.run_us.server.domains.crew.service.usecase.CrewJoinUseCase;
import com.run_us.server.global.common.SuccessResponse;

import com.run_us.server.global.security.annotation.CurrentUser;
import com.run_us.server.global.security.principal.UserPrincipal;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/crews")
@RequiredArgsConstructor
public class CrewController {
    private final CrewJoinUseCase crewJoinUseCase;

    @PostMapping("/{crewPublicId}/join-requests")
    public ResponseEntity<SuccessResponse<CreateJoinRequestResponse>> requestJoin(
            @PathVariable String crewPublicId,
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody CreateJoinRequest request
    ) {
        log.info("action=request_join crewPublicId={} userPublicId={}", crewPublicId, userPrincipal.getPublicId());
        CrewJoinRequestInternalResponse response = crewJoinUseCase.createJoinRequest(crewPublicId, userPrincipal.getInternalId(), request);
        return ResponseEntity.ok(
                SuccessResponse.of(
                        CrewHttpResponseCode.JOIN_REQUEST_CREATED,
                        response.toPublicCreateResponse(userPrincipal.getPublicId())));
    }
}
