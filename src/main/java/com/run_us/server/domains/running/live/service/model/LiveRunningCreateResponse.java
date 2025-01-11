package com.run_us.server.domains.running.live.service.model;

import com.run_us.server.domains.running.run.domain.Run;
import lombok.Getter;

@Getter
public class LiveRunningCreateResponse {
  private final String runPublicId;
  private final String passcode;

  public LiveRunningCreateResponse(String runPublicId, String passcode) {
    this.runPublicId = runPublicId;
    this.passcode = passcode;
  }
  public static LiveRunningCreateResponse from (Run run, String passcode) {
    return new LiveRunningCreateResponse(run.getPublicId(), passcode);
  }
}
