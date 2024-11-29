package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.domain.RunningPreview;
import lombok.Getter;

@Getter
public class UpdatePreviewResponse {
  private final Integer runId;
  private final RunningPreview preview;

  public UpdatePreviewResponse(Integer runId, RunningPreview preview) {
    this.runId = runId;
    this.preview = preview;
  }
}
