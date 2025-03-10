package com.run_us.server.domains.running.run.domain;

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
  private RunType type;
  private String meetingPoint;
  private SessionAccessLevel accessLevel = SessionAccessLevel.ALLOW_ALL;
  private ZonedDateTime beginTime;

  @Builder
  public RunningPreview(
      String title,
      String description,
      RunType type,
      String meetingPoint,
      SessionAccessLevel accessLevel,
      ZonedDateTime beginTime) {
    this.title = title;
    this.description = description;
    this.type = type;
    this.meetingPoint = meetingPoint;
    this.accessLevel = accessLevel;
    this.beginTime = beginTime;
  }

  // TODO : run type 추가
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
