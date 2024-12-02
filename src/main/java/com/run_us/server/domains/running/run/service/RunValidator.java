package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.Run;
import org.springframework.stereotype.Component;

@Component
public final class RunValidator {
  public void validateIsRunOwner(Integer userId, Run run) {
    if (!run.isHost(userId)) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND);
    }
  }
}
