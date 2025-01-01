package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.domain.RunPace;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class JoinedRunPreviewResponse {
  private String runningPublicId;
  private String title;
  private String description;
  private ZonedDateTime startAt;
  private Long participantCount;
  private HostInfo createdBy;
  @Setter
  private List<RunPace> runPaces;

  public JoinedRunPreviewResponse(String runningPublicId,
                                  RunningPreview runningPreview,
                                  Long participantCount,
                                  String userPublicId, String name, String imgUrl) {
    this.runningPublicId = runningPublicId;
    this.title = runningPreview.getTitle();
    this.description = runningPreview.getDescription();
    this.startAt = runningPreview.getBeginTime();
    this.participantCount = participantCount;
    this.createdBy = new HostInfo(userPublicId, name, imgUrl);
  }



}
