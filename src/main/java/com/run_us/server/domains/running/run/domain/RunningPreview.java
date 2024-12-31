package com.run_us.server.domains.running.run.domain;

import com.run_us.server.domains.running.run.controller.model.request.SessionAccessLevel;
import com.run_us.server.domains.running.run.service.model.RunCreateDto;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningPreview implements Serializable {
  private String title;
  private String description;
  private String meetingPoint;
  private SessionAccessLevel accessLevel;
  private ZonedDateTime beginTime;

  @Builder
  public RunningPreview(
      String title,
      String description,
      String meetingPoint,
      SessionAccessLevel accessLevel,
      ZonedDateTime beginTime) {
    this.title = title;
    this.description = description;
    this.meetingPoint = meetingPoint;
    this.accessLevel = accessLevel;
    this.beginTime = beginTime;
  }

  public static RunningPreview from(RunCreateDto runCreateDto) {
    return RunningPreview.builder()
        .title(runCreateDto.getTitle())
        .description(runCreateDto.getDescription())
        .meetingPoint(runCreateDto.getMeetingPoint())
        .accessLevel(runCreateDto.getAccessLevel())
        .beginTime(runCreateDto.getBeginTime())
        .build();
  }
}
