package com.run_us.server.v2.running.run.controller;

import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.v2.RunningHttpResponseCode;
import com.run_us.server.v2.running.run.controller.model.request.SessionRunCreateRequest;
import com.run_us.server.v2.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.v2.running.run.service.model.ParticipantInfo;
import com.run_us.server.v2.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.v2.running.run.service.usecase.RunCreateUseCase;
import com.run_us.server.v2.running.run.service.usecase.RunRegisterUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
//임시로 /runs로 변경
@RequestMapping("/runs")
@RequiredArgsConstructor
public class RunController {

  private final RunCreateUseCase runCreateUseCase;
  private final RunRegisterUseCase runRegisterUseCase;

  @PostMapping(params = "mode=custom")
  public SuccessResponse<CustomRunCreateResponse> createCustomRun(
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=create_custom_running user_id={}", userId);
    return SuccessResponse.of(
        RunningHttpResponseCode.RUNNING_CREATED, runCreateUseCase.saveNewCustomRun(userId));
  }

  @PostMapping(params = "mode=normal")
  public SuccessResponse<SessionRunCreateResponse> createNormalRun(
      @RequestBody SessionRunCreateRequest runningCreateRequest,
      @RequestAttribute("publicUserId") String userId) {
    log.info("action=create_session_running user_id={}", userId);
    return SuccessResponse.of(
        RunningHttpResponseCode.RUNNING_CREATED,
        runCreateUseCase.saveNewSessionRun(
            userId, runningCreateRequest.toRunningPreview()));
  }

  @GetMapping("/{runningId}/participants")
  public SuccessResponse<List<ParticipantInfo>> joinedParticipants(@PathVariable String runningId) {
    log.info("action=joined_participants running_id={}", runningId);
    List<ParticipantInfo> joinedParticipants = runRegisterUseCase.getRunParticipants(runningId);
    return SuccessResponse.of(RunningHttpResponseCode.PARTICIPANTS_FETCHED, joinedParticipants);
  }

}