package com.run_us.server.domains.running.live.service.usecase;

import com.run_us.server.domains.running.live.service.model.LiveRunningCreateResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface CreateLiveRunningUseCase {
  SuccessResponse<LiveRunningCreateResponse> createLiveRunning(String runPublicId, Integer userId);
}
