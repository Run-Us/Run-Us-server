package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.v2.running.run.domain.RunningPreview;
import com.run_us.server.v2.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.v2.running.run.service.model.SessionRunCreateResponse;

public interface RunCreateUseCase {
  CustomRunCreateResponse saveNewCustomRun(String userId);

  SessionRunCreateResponse saveNewSessionRun(String userId, RunningPreview runningPreview);
}
