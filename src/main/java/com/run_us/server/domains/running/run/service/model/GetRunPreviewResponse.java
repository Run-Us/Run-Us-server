package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.domain.RunPace;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetRunPreviewResponse {
  private final String hostName;
  private final String hostImgUrl;
  @Setter
  private List<RunPace> runPaces;
  private final RunningPreview preview;

  public GetRunPreviewResponse(String hostName, String hostImgUrl, RunningPreview preview) {
    this.hostName = hostName;
    this.hostImgUrl = hostImgUrl;
    this.preview = preview;
  }
}
