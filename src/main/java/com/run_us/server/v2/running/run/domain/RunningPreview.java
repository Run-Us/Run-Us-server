package com.run_us.server.v2.running.run.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningPreview implements Serializable {
  private String title;
  private String description;
  private String meetingPoint;
  private RunPace paceTag;
  private String goal;
  private String accessLevel;
  private ZonedDateTime beginTime;

  @Builder
  public RunningPreview(
      String title,
      String description,
      String meetingPoint,
      RunPace paceTag,
      String goal,
      String accessLevel,
      ZonedDateTime beginTime) {
    this.title = title;
    this.description = description;
    this.meetingPoint = meetingPoint;
    this.paceTag = paceTag;
    this.goal = goal;
    this.accessLevel = accessLevel;
    this.beginTime = beginTime;
  }
}
