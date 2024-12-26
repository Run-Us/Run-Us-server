package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.Run;
import org.springframework.stereotype.Component;

@Component
public final class RunValidator {

  public void validateRunDeletable(Integer userId, Run run) {
    validateIsRunOwner(userId, run);
    if (run.isDeletable()) {
      throw RunningException.of(RunningErrorCode.RUNNING_SESSION_NOT_MODIFIABLE);
    }
  }

  public void validateIsRunOwner(Integer userId, Run run) {
    if (!run.isHost(userId)) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND);
    }
  }
}
