package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.v2.running.run.domain.RunningPreview;
import com.run_us.server.v2.running.run.service.model.RunCreateResponse;

public interface RunCreateUseCase {
  RunCreateResponse saveNewCustomRun(String userId);

  RunCreateResponse saveNewSessionRun(String userId, RunningPreview runningPreview);
}
