package com.run_us.server.v2.running.run.service.model;

import com.run_us.server.v2.running.run.domain.Run;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionRunCreateResponse {
  private String runningId;

  public SessionRunCreateResponse(String runningId) {
    this.runningId = runningId;
  }

  public static SessionRunCreateResponse from (Run run) {
    return new SessionRunCreateResponse(run.getPublicId());
  }
}
