package com.run_us.server.v2.running.run.service.model;

import com.run_us.server.v2.running.run.domain.Run;
import lombok.Getter;

@Getter
public class CustomRunCreateResponse {
  private final String runningId;
  private final String passcode;

  private CustomRunCreateResponse(String runningId, String passcode) {
    this.runningId = runningId;
    this.passcode = passcode;
  }

  public static CustomRunCreateResponse from(Run run, String passcode) {
    return new CustomRunCreateResponse(run.getPublicId(), passcode);
  }
}
