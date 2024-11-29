package com.run_us.server.domains.user.service.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyRunningRecord {
  private final ZonedDateTime startedAt;
  private final Integer runningDistanceInMeters;
  private final Integer runningDurationInMilliseconds;
  private final Integer averagePaceInMilliseconds;

  @Builder
  public MyRunningRecord(
      ZonedDateTime startedAt,
      Integer runningDistanceInMeters,
      Integer runningDurationInMilliseconds,
      Integer averagePaceInMilliseconds) {
    this.startedAt = startedAt;
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliseconds = runningDurationInMilliseconds;
    this.averagePaceInMilliseconds = averagePaceInMilliseconds;
  }
}
