package com.run_us.server.domains.crew.controller;

import com.run_us.server.domains.crew.controller.model.enums.CrewHttpResponseCode;
import com.run_us.server.domains.crew.controller.model.request.CreateJoinRequest;
import com.run_us.server.domains.crew.controller.model.request.ReviewJoinRequest;
import com.run_us.server.domains.crew.controller.model.response.*;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.service.usecase.CrewJoinUseCase;
import com.run_us.server.global.common.SuccessResponse;

import com.run_us.server.global.security.annotation.CurrentUser;
import com.run_us.server.global.security.principal.UserPrincipal;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{crewPublicId}/join-requests")
    public ResponseEntity<SuccessResponse<CancelJoinRequestResponse>> cancelJoinRequest(
            @PathVariable String crewPublicId,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        log.info("action=cancel_join_request crewPublicId={} userPublicId={}", crewPublicId, userPrincipal.getPublicId());
        CrewJoinRequestInternalResponse response = crewJoinUseCase.cancelJoinRequest(crewPublicId, userPrincipal.getInternalId());
        return ResponseEntity.ok(
                SuccessResponse.of(
                        CrewHttpResponseCode.JOIN_REQUEST_CANCELLED,
                        response.toPublicCancelResponse()
                )
        );
    }

    @GetMapping("/{crewPublicId}/join-requests")
    public ResponseEntity<SuccessResponse<List<FetchJoinRequestResponse>>> getJoinRequests(
            @PathVariable String crewPublicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        log.info("action=get_join_requests_start crewPublicId={} page={} limit={}", crewPublicId, page, limit);

        List<FetchJoinRequestResponse> responses = crewJoinUseCase.getJoinRequests(
                crewPublicId,
                PageRequest.of(page, limit),
                userPrincipal.getInternalId()
        );

        log.info("action=get_join_requests_complete crewPublicId={} page={} limit={} result_count={}",
                crewPublicId, page, limit, responses.size());
        return ResponseEntity.ok(
                SuccessResponse.of(
                        CrewHttpResponseCode.JOIN_REQUEST_FETCHED,
                        responses
                )
        );
    }

    @PutMapping("/{crewPublicId}/join-requests/{requestId}")
    public ResponseEntity<SuccessResponse<ReviewJoinRequestResponse>> reviewJoinRequest(
            @PathVariable String crewPublicId,
            @PathVariable Integer requestId,
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody ReviewJoinRequest request
    ) {
        log.info("action=process_join_request_start crewPublicId={} requestId={} status={}",
                crewPublicId, requestId, request.getStatus());

        ReviewJoinRequestResponse response = crewJoinUseCase.reviewJoinRequest(
                crewPublicId,
                requestId,
                CrewJoinRequestStatus.valueOf(request.getStatus()),
                userPrincipal.getInternalId()
        );

        log.info("action=process_join_request_complete crewPublicId={} requestId={}",
                crewPublicId, requestId);
        return ResponseEntity.ok(
                SuccessResponse.of(
                        CrewHttpResponseCode.JOIN_REQUEST_REVIEWED,
                        response
                )
        );
    }
}
