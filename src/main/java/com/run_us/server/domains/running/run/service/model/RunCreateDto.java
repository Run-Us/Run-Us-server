package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.controller.model.request.SessionAccessLevel;
import com.run_us.server.domains.running.run.domain.RunPace;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class RunCreateDto {
  private String title;
  private String description;
  private String meetingPoint;
  private SessionAccessLevel accessLevel;
  private ZonedDateTime beginTime;
  private List<RunPace> runPaces;

  public RunCreateDto(String title, String description, String meetingPoint, SessionAccessLevel accessLevel, ZonedDateTime beginTime, List<RunPace> runPaces) {
    this.title = title;
    this.description = description;
    this.meetingPoint = meetingPoint;
    this.accessLevel = accessLevel;
    this.beginTime = beginTime;
    this.runPaces = runPaces;
  }
}
