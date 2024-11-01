package com.run_us.server.domains.running.controller.model.request;

import com.run_us.server.domains.running.service.model.RunningAggregations;
import lombok.Getter;

@Getter
public class MultiRunRecordRequest {
  private final String runningId;
  private final String recordData;
  private final int runningDistanceInMeters;
  private final int runningDurationInMilliSeconds;
  private final int averagePaceInMilliSeconds;

  public MultiRunRecordRequest(String runningId, String recordData, int runningDistanceInMeters, int runningDurationInMilliSeconds, int averagePaceInMilliSeconds) {
    this.runningId = runningId;
    this.recordData = recordData;
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliSeconds = runningDurationInMilliSeconds;
    this.averagePaceInMilliSeconds = averagePaceInMilliSeconds;
  }

  public static RunningAggregations toRunningAggregations(MultiRunRecordRequest request) {
    return new RunningAggregations(
        request.getRecordData(),
        request.getRunningDistanceInMeters(),
        request.getRunningDurationInMilliSeconds(),
        request.getAveragePaceInMilliSeconds()
    );
  }
}
