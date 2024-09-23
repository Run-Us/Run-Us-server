package com.run_us.server.domains.running.controller.model.request;

import com.run_us.server.domains.running.domain.LocationData.RunnerPos;
import com.run_us.server.domains.running.domain.RunningConstraints;
import com.run_us.server.domains.running.domain.RunningDescription;
import lombok.Getter;

@Getter
public class RunningCreateRequest {
  private final RunningConstraints constraints;
  private final RunningDescription description;
  private final RunnerPos startLocation;


  public RunningCreateRequest(RunningConstraints constraints, RunningDescription description, RunnerPos startLocation) {
    this.constraints = constraints;
    this.description = description;
    this.startLocation = startLocation;
  }
}
