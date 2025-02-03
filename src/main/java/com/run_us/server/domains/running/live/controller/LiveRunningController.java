package com.run_us.server.domains.running.live.controller;

import com.run_us.server.domains.running.live.service.model.LiveRunningCreateResponse;
import com.run_us.server.domains.running.live.service.usecase.CreateLiveRunningUseCase;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.security.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/runnings/live")
@RequiredArgsConstructor
public class LiveRunningController {

  private final CreateLiveRunningUseCase createLiveRunningUseCase;

  @PostMapping()
  public ResponseEntity<SuccessResponse<LiveRunningCreateResponse>> createLiveRunning(
      @RequestParam("runPublicId") String runPublicId,
      @CurrentUser String currentUserPublicId) {
    SuccessResponse<LiveRunningCreateResponse> response =
        createLiveRunningUseCase.createLiveRunning(runPublicId, currentUserPublicId);
    return ResponseEntity.ok(response);
  }
}
