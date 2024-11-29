package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.domains.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.domains.running.run.domain.RunningPreview;

public interface RunCreateUseCase {
  CustomRunCreateResponse saveNewCustomRun(String userId);

  SessionRunCreateResponse saveNewSessionRun(String userId, RunningPreview runningPreview);
}
