package com.run_us.server.v2.running.run.service.model;

import com.run_us.server.v2.running.run.domain.RunningPreview;
import lombok.Getter;

@Getter
public class GetRunPreviewResponse {
  private final String hostName;
  private final String hostImgUrl;
  private final RunningPreview preview;

  public GetRunPreviewResponse(String hostName, String hostImgUrl, RunningPreview preview) {
    this.hostName = hostName;
    this.hostImgUrl = hostImgUrl;
    this.preview = preview;
  }
}
