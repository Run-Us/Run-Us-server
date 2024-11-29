package com.run_us.server.domains.running.record.controller.model;

import com.run_us.server.domains.running.record.domain.RecordStats;
import lombok.Getter;

@Getter
public class GroupRunRecordRequest {
  private final String runningId;
  private final int runningDistanceInMeters;
  private final int runningDurationInMilliSeconds;
  private final int averagePaceInMilliSeconds;

  public GroupRunRecordRequest(
      String runningId,
      int runningDistanceInMeters,
      int runningDurationInMilliSeconds,
      int averagePaceInMilliSeconds) {
    this.runningId = runningId;

    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliSeconds = runningDurationInMilliSeconds;
    this.averagePaceInMilliSeconds = averagePaceInMilliSeconds;
  }

  public RecordStats toRecordStats() {
    return new RecordStats(
        runningDistanceInMeters, runningDurationInMilliSeconds, averagePaceInMilliSeconds);
  }
}
