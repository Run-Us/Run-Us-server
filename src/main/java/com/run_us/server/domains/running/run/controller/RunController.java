package com.run_us.server.domains.running.run.controller;

import com.run_us.server.domains.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.domains.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.domains.running.run.controller.model.request.SessionRunCreateRequest;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import com.run_us.server.domains.running.run.service.usecase.RunCreateUseCase;
import com.run_us.server.domains.running.run.service.usecase.RunRegisterUseCase;
import com.run_us.server.global.common.SuccessResponse;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/runnings")
@RequiredArgsConstructor
public class RunController {

  private final RunCreateUseCase runCreateUseCase;
  private final RunRegisterUseCase runRegisterUseCase;

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
    SuccessResponse<SessionRunCreateResponse> response = runCreateUseCase.saveNewSessionRun(userId, runningCreateRequest.toRunningPreview());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{runningId}/participants")
  public ResponseEntity<SuccessResponse<List<ParticipantInfo>>> joinedParticipants(@PathVariable String runningId) {
    log.info("action=joined_participants running_id={}", runningId);
    SuccessResponse<List<ParticipantInfo>> response = runRegisterUseCase.getRunParticipants(runningId);
    return ResponseEntity.ok().body(response);
  }
}