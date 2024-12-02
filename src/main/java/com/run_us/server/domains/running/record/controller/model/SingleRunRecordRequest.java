package com.run_us.server.domains.running.record.controller.model;

import com.run_us.server.domains.running.record.domain.RecordStats;
import lombok.Getter;

@Getter
public class SingleRunRecordRequest {
  private final int runningDistanceInMeters;
  private final int runningDurationInMilliSeconds;
  private final int averagePaceInMilliSeconds;

  public SingleRunRecordRequest(
      int runningDistanceInMeters,
      int runningDurationInMilliSeconds,
      int averagePaceInMilliSeconds) {
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliSeconds = runningDurationInMilliSeconds;
    this.averagePaceInMilliSeconds = averagePaceInMilliSeconds;
  }

  public RecordStats toRecordStats() {
    return new RecordStats(
        runningDistanceInMeters, runningDurationInMilliSeconds, averagePaceInMilliSeconds);
  }
}
