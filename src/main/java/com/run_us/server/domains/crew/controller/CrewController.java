package com.run_us.server.domains.crew.controller;

import com.run_us.server.domains.crew.controller.model.request.*;
import com.run_us.server.domains.crew.controller.model.response.*;
import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.domains.crew.service.usecase.CommandCrewUseCase;
import com.run_us.server.domains.crew.service.usecase.CrewJoinUseCase;
import com.run_us.server.domains.crew.controller.model.response.CreateCrewResponse;
import com.run_us.server.domains.crew.service.usecase.CreateCrewUseCase;
import com.run_us.server.domains.crew.service.usecase.CrewMemberUseCase;
import com.run_us.server.global.common.SuccessResponse;

import com.run_us.server.global.security.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final CreateCrewUseCase createCrewUseCase;
    private final CrewMemberUseCase crewMemberUseCase;
    private final CommandCrewUseCase commandCrewUseCase;


    @PostMapping
    public ResponseEntity<SuccessResponse<CreateCrewResponse>> createCrew(
            @RequestBody CreateCrewRequest requestDto,
            @RequestAttribute("publicUserId") String userId) {
        log.info("action=create_crew user_id={}", userId);

        SuccessResponse<CreateCrewResponse> response = createCrewUseCase.createCrew(requestDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{crewPublicId}")
    public ResponseEntity<SuccessResponse<UpdateCrewInfoResponse>> updateCrewInfo(
            @PathVariable String crewPublicId,
            @RequestBody UpdateCrewInfoRequest requestDto,
            @CurrentUser String currentUserPublicId){
        log.info("action=update_crew_info userPublicId={}, crewPublicId={}", currentUserPublicId, crewPublicId);

        SuccessResponse<UpdateCrewInfoResponse> response = commandCrewUseCase.updateCrewInfo(crewPublicId, requestDto, currentUserPublicId);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{crewPublicId}/join-rule")
    public ResponseEntity<SuccessResponse<UpdateCrewJoinRuleResponse>> updateCrewJoinRule(
            @PathVariable String crewPublicId,
            @RequestBody UpdateCrewJoinTypeRequest requestDto,
            @CurrentUser String currentUserPublicId){
        log.info("action=update_crew_join_rule userPublicId={}, crewPublicId={}", currentUserPublicId, crewPublicId);

        SuccessResponse<UpdateCrewJoinRuleResponse> response = commandCrewUseCase.updateCrewJoinRule(crewPublicId, requestDto, currentUserPublicId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{crewPublicId}/join-requests")
    public ResponseEntity<SuccessResponse<CreateJoinRequestResponse>> requestJoin(
            @PathVariable String crewPublicId,
            @CurrentUser String currentUserPublicId,
            @Valid @RequestBody CreateJoinRequest request
    ) {
        log.info("action=request_join crewPublicId={} userPublicId={}", crewPublicId, currentUserPublicId);
        SuccessResponse<CreateJoinRequestResponse> response = crewJoinUseCase.createJoinRequest(crewPublicId, currentUserPublicId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{crewPublicId}/join-requests")
    public ResponseEntity<SuccessResponse<CancelJoinRequestResponse>> cancelJoinRequest(
            @PathVariable String crewPublicId,
            @CurrentUser String currentUserPublicId
    ) {
        log.info("action=cancel_join_request crewPublicId={} userPublicId={}", crewPublicId, currentUserPublicId);
        SuccessResponse<CancelJoinRequestResponse> response = crewJoinUseCase.cancelJoinRequest(crewPublicId, currentUserPublicId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{crewPublicId}/join-requests")
    public ResponseEntity<SuccessResponse<List<FetchJoinRequestResponse>>> getJoinRequests(
            @PathVariable String crewPublicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @CurrentUser String currentUserPublicId
    ) {
        log.info("action=get_join_requests_start crewPublicId={} page={} limit={}", crewPublicId, page, limit);

        SuccessResponse<List<FetchJoinRequestResponse>> responses = crewJoinUseCase.getJoinRequests(
                crewPublicId,
                PageRequest.of(page, limit),
                currentUserPublicId
        );

        log.info("action=get_join_requests_complete crewPublicId={} page={} limit={} result_count={}",
                crewPublicId, page, limit, responses.getPayload().size());
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{crewPublicId}/join-requests/{requestId}")
    public ResponseEntity<SuccessResponse<ReviewJoinRequestResponse>> reviewJoinRequest(
            @PathVariable String crewPublicId,
            @PathVariable Integer requestId,
            @CurrentUser String currentUserPublicId,
            @Valid @RequestBody ReviewJoinRequest request
    ) {
        log.info("action=process_join_request_start crewPublicId={} requestId={} status={}",
                crewPublicId, requestId, request.getStatus());

        SuccessResponse<ReviewJoinRequestResponse> response = crewJoinUseCase.reviewJoinRequest(
                crewPublicId,
                requestId,
                CrewJoinRequestStatus.valueOf(request.getStatus()),
                currentUserPublicId
        );

        log.info("action=process_join_request_complete crewPublicId={} requestId={}",
                crewPublicId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{crewPublicId}/members")
    public ResponseEntity<SuccessResponse<List<FetchMemberResponse>>> getMembers(
        @PathVariable String crewPublicId,
        @CurrentUser String currentUserPublicId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int limit
    ) {
        log.info("action=get_members_request_start crewPublicId={} currentUserPublicId={}",
            crewPublicId, currentUserPublicId);

        SuccessResponse<List<FetchMemberResponse>> response =
            crewMemberUseCase.getMembers(crewPublicId, currentUserPublicId, PageRequest.of(page, limit));

        log.info("action=get_members_request_end crewPublicId={} currentUserPublicId={}",
            crewPublicId, currentUserPublicId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{crewPublicId}/members/{userPublicId}")
    public ResponseEntity<SuccessResponse<KickMemberResponse>> kickMember(
        @PathVariable String crewPublicId,
        @PathVariable String userPublicId,
        @CurrentUser String currentUserPublicId
    ) {
        log.info("action=remove_member_request_start crewPublicId={} userPublicId={} currentUserPublicId={}",
            crewPublicId, userPublicId, currentUserPublicId);

        SuccessResponse<KickMemberResponse> response =
            crewMemberUseCase.kickMember(crewPublicId, currentUserPublicId, userPublicId);

        log.info("action=remove_member_request_end crewPublicId={} userPublicId={} currentUserPublicId={}",
            crewPublicId, userPublicId, currentUserPublicId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
