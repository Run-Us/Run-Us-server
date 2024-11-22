package com.run_us.server.v2.running.record.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordStats {
  private int runningDistanceInMeters;
  private int runningTimeInSeconds;
  private int runningPaceInSeconds;

  public RecordStats(
      int runningDistanceInMeters, int runningTimeInSeconds, int runningPaceInSeconds) {
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningTimeInSeconds = runningTimeInSeconds;
    this.runningPaceInSeconds = runningPaceInSeconds;
  }
}
