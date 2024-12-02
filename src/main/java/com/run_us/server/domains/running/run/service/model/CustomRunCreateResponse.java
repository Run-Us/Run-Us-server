package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.domain.Run;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomRunCreateResponse {
  private String runningPublicId;
  private String passcode;

  private CustomRunCreateResponse(String runningPublicId, String passcode) {
    this.runningPublicId = runningPublicId;
    this.passcode = passcode;
  }

  public static CustomRunCreateResponse from(Run run, String passcode) {
    return new CustomRunCreateResponse(run.getPublicId(), passcode);
  }
}
