package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;

public interface RunPreviewUseCase {
  void updateRunPreview(String userId, Integer runId, RunningPreview preview);

  GetRunPreviewResponse getRunPreview(Integer runId);
}
