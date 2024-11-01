package com.run_us.server.domains.running.service.model;

import lombok.Getter;

@Getter
public class RunningAggregations {
  private final String recordData;
  private final int runningDistanceInMeters;
  private final int runningDurationInMilliSeconds;
  private final int averagePaceInMilliSeconds;

  public RunningAggregations(
      String recordData,
      int runningDistanceInMeters,
      int runningDurationInMilliSeconds,
      int averagePaceInMilliSeconds) {
    this.recordData = recordData;
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliSeconds = runningDurationInMilliSeconds;
    this.averagePaceInMilliSeconds = averagePaceInMilliSeconds;
  }
}
