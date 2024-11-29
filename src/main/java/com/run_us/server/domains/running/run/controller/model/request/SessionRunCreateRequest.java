package com.run_us.server.domains.running.run.controller.model.request;

import com.run_us.server.domains.running.run.domain.RunPace;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SessionRunCreateRequest {
  private final String title;
  private final String description;
  private final ZonedDateTime beginTime;
  private final String meetingPoint;
  private final String goal;
  private final String accessLevel;
  private final List<String> exposingCrews;
  private final RunPace paceTag;

  @Builder
  public SessionRunCreateRequest(
      String title,
      String description,
      ZonedDateTime beginTime,
      String meetingPoint,
      String goal,
      String accessLevel,
      List<String> exposingCrews,
      RunPace paceTag) {
    this.title = title;
    this.description = description;
    this.beginTime = beginTime;
    this.meetingPoint = meetingPoint;
    this.goal = goal;
    this.accessLevel = accessLevel;
    this.exposingCrews = exposingCrews;
    this.paceTag = paceTag;
  }

  public RunningPreview toRunningPreview() {
    return RunningPreview.builder()
        .title(title)
        .description(description)
        .beginTime(beginTime)
        .meetingPoint(meetingPoint)
        .goal(goal)
        .accessLevel(accessLevel)
        .paceTag(paceTag)
        .build();
  }
}
