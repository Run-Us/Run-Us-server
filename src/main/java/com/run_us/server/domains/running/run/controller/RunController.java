package com.run_us.server.domains.running.run.controller;

import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.controller.model.request.SessionRunCreateRequest;
import com.run_us.server.domains.running.run.service.model.*;
import com.run_us.server.domains.running.run.service.usecase.RunCreateUseCase;
import com.run_us.server.domains.running.run.service.usecase.RunDeleteUseCase;
import com.run_us.server.domains.running.run.service.usecase.RunPreviewUseCase;
import com.run_us.server.domains.running.run.service.usecase.RunRegisterUseCase;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/runnings")
@RequiredArgsConstructor
public class RunController {

  private final RunCreateUseCase runCreateUseCase;
  private final RunRegisterUseCase runRegisterUseCase;
  private final RunPreviewUseCase runPreviewUseCase;
  private final RunDeleteUseCase runDeleteUseCase;

  @PostMapping(params = "mode=custom")
  public ResponseEntity<SuccessResponse<CustomRunCreateResponse>> createCustomRun(
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=create_custom_running user_id={}", userId);
    SuccessResponse<CustomRunCreateResponse> response = runCreateUseCase.saveNewCustomRun(userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping(params = "mode=normal")
  public ResponseEntity<SuccessResponse<SessionRunCreateResponse>> createNormalRun(
      @RequestBody SessionRunCreateRequest runningCreateRequest,
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=create_session_running user_id={}", userId);
    SuccessResponse<SessionRunCreateResponse> response = runCreateUseCase.saveNewSessionRun(userId, runningCreateRequest.toRunCreateDto());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/{runningId}/registration")
  public ResponseEntity<SuccessResponse<List<ParticipantInfo>>> registerRun(
      @PathVariable String runningId,
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=register_running running_id={}", runningId);
    SuccessResponse<List<ParticipantInfo>> response = runRegisterUseCase.registerRun(userId, runningId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{runningId}/participants")
  public ResponseEntity<SuccessResponse<List<ParticipantInfo>>> joinedParticipants(@PathVariable String runningId) {
    log.info("action=joined_participants running_id={}", runningId);
    SuccessResponse<List<ParticipantInfo>> response = runRegisterUseCase.getRunParticipants(runningId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/id")
  public ResponseEntity<SuccessResponse<FetchRunningIdResponse>> fetchRunningIdWithPasscode(
      @RequestParam String passcode) {
    log.info("action=fetch_running_id_with_passcode passcode={}", passcode);
    SuccessResponse<FetchRunningIdResponse> response = runRegisterUseCase.getRunningIdWithPasscode(passcode);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{runningId}")
  public ResponseEntity<SuccessResponse<Void>> deleteRunning(
      @RequestAttribute("publicUserId") String userId,
      @PathVariable String runningId) {
    log.info("action=delete_running running_id={} user_id={}", runningId, userId);
    runDeleteUseCase.deleteRun(userId, runningId);
    return ResponseEntity.ok(SuccessResponse.messageOnly(RunningHttpResponseCode.RUN_PREVIEW_DELETED));
  }

  @GetMapping("/registrations")
  public ResponseEntity<SuccessResponse<List<JoinedRunPreviewResponse>>> getJoinedRunnings(
      @RequestAttribute("publicUserId") String userId,
      @RequestParam(defaultValue = "0") int runningPage,
      @RequestParam(defaultValue = "10") int runningSize){
    log.info("action=get_joined_runnings user_id={}", userId);
    SuccessResponse<List<JoinedRunPreviewResponse>> response = runPreviewUseCase.getJoinedRunPreview(userId, runningPage, runningSize);
    return ResponseEntity.ok().body(response);
  }
}