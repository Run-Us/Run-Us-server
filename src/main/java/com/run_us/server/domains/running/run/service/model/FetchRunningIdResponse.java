package com.run_us.server.domains.running.run.service.model;

import lombok.Getter;

@Getter
public class FetchRunningIdResponse {
  private final String runningPublicId;
  private final String passcode;

  public FetchRunningIdResponse(String runningId, String passcode) {
    this.runningPublicId = runningId;
    this.passcode = passcode;
  }
}
