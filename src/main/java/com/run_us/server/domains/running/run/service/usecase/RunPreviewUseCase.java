package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.service.model.GetRunPreviewResponse;
import com.run_us.server.domains.running.run.service.model.UpdatePreviewResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface RunPreviewUseCase {
  SuccessResponse<UpdatePreviewResponse> updateRunPreview(String userId, Integer runId, RunningPreview preview);

  SuccessResponse<GetRunPreviewResponse> getRunPreview(Integer runId);
}
