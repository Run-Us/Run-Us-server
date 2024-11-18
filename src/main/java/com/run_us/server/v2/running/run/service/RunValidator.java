package com.run_us.server.v2.running.run.service;

import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.v2.RunningException;
import com.run_us.server.v2.running.run.domain.Run;
import org.springframework.stereotype.Component;

@Component
public final class RunValidator {
  public void validateIsRunOwner(Integer userId, Run run) {
    if (!run.isHost(userId)) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND);
    }
  }
}
