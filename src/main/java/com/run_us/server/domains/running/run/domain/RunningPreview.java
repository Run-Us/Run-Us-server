package com.run_us.server.domains.running.run.domain;

import com.run_us.server.domains.running.run.controller.model.request.SessionAccessLevel;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningPreview implements Serializable {
  private String title;
  private String description;
  private String meetingPoint;
  @ElementCollection(targetClass = RunPace.class)
  @Enumerated
  private List<RunPace> paceCategories = new ArrayList<>();
  private SessionAccessLevel accessLevel;
  private ZonedDateTime beginTime;

  @Builder
  public RunningPreview(
      String title,
      String description,
      String meetingPoint,
      List<RunPace> paceCategories,
      SessionAccessLevel accessLevel,
      ZonedDateTime beginTime) {
    this.title = title;
    this.description = description;
    this.meetingPoint = meetingPoint;
    this.paceCategories = paceCategories;
    this.accessLevel = accessLevel;
    this.beginTime = beginTime;
  }
}
