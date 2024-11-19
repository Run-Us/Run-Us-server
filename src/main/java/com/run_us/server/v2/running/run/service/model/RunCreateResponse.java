package com.run_us.server.v2.running.run.service.model;

import com.run_us.server.v2.running.run.domain.Run;
import lombok.Getter;

@Getter
public class RunCreateResponse {
  private final String runningId;
  private final String passcode;

  private RunCreateResponse(String runningId, String passcode) {
    this.runningId = runningId;
    this.passcode = passcode;
  }

  public static RunCreateResponse from(Run run) {
    return new RunCreateResponse(run.getPublicId(), run.getPasscode());
  }
}
