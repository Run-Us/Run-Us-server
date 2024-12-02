package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionRunCreateResponse {
  private String runningPublicId;
  private RunningPreview runningPreview;

  private SessionRunCreateResponse(String runningPublicId, RunningPreview runningPreview) {
    this.runningPublicId = runningPublicId;
    this.runningPreview = runningPreview;
  }

  public static SessionRunCreateResponse from(Run run) {
    return new SessionRunCreateResponse(run.getPublicId(), run.getPreview());
  }
}
