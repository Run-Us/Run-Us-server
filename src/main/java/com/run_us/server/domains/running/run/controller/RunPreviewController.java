package com.run_us.server.domains.running.run.controller;

import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;
import com.run_us.server.domains.running.run.service.usecase.RunPreviewUseCase;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/runnings/previews")
@RequiredArgsConstructor
public class RunPreviewController {

  private final RunPreviewUseCase runPreviewUseCase;

  @GetMapping("/{runId}")
  public ResponseEntity<SuccessResponse<GetRunPreviewResponse>> getRunPreview(@RequestParam Integer runId) {
    log.info("action=get_run_preview");
    SuccessResponse<GetRunPreviewResponse> response = runPreviewUseCase.getRunPreview(runId);
    return ResponseEntity.ok().body(response);
  }
}
