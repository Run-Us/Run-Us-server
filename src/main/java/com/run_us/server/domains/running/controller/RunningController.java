package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.controller.model.request.RunningCreateRequest;
import com.run_us.server.domains.running.service.RunningPreparationService;
import com.run_us.server.domains.running.service.model.JoinedParticipant;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exception.code.ExampleErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/runnings")
@RequiredArgsConstructor
public class RunningController {

  private final RunningPreparationService runningPreparationService;

  @PostMapping
  public SuccessResponse createRunning(@RequestBody RunningCreateRequest runningCreateDto) {
    return SuccessResponse.of(ExampleErrorCode.SUCCESS,
        runningPreparationService.createRunning(runningCreateDto));
  }

  @GetMapping("/{runningId}/participants")
  public SuccessResponse joinedParticipants(@PathVariable String runningId) {
    List<JoinedParticipant> joinedParticipants = runningPreparationService.getJoinedParticipants(
        runningId);
    return SuccessResponse.of(ExampleErrorCode.SUCCESS, joinedParticipants);
  }
}
