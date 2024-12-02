package com.run_us.server.domains.running.record.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordStats implements Serializable {
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
