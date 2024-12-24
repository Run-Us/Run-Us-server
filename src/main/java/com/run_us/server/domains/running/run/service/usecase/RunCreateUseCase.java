package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.domains.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface RunCreateUseCase {
  SuccessResponse<CustomRunCreateResponse> saveNewCustomRun(String userId);

  SuccessResponse<SessionRunCreateResponse> saveNewSessionRun(String userId, RunningPreview runningPreview);
}
