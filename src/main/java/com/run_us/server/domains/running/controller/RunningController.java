package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.controller.model.request.RunningCreateRequest;
import com.run_us.server.domains.running.service.RunningPreparationService;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exceptions.enums.ExampleErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/running")
@RequiredArgsConstructor
public class RunningController {

  private final RunningPreparationService runningPreparationService;

  @PostMapping
  public SuccessResponse createRunning(@RequestBody RunningCreateRequest runningCreateDto) {
    return SuccessResponse.of(ExampleErrorCode.SUCCESS, runningPreparationService.createRunning(runningCreateDto));
  }
}
