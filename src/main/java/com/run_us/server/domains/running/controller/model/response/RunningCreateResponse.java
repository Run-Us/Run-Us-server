package com.run_us.server.domains.running.controller.model.response;

import com.run_us.server.domains.running.domain.running.Running;
import lombok.Getter;

@Getter
public final class RunningCreateResponse {
  private final String runningId;
  private final String passcode;

  private RunningCreateResponse(String runningId, String passcode) {
    this.runningId = runningId;
    this.passcode = passcode;
  }

  public static RunningCreateResponse from(Running running) {
    return new RunningCreateResponse(running.getPublicKey(), running.getPasscode());
  }
}
