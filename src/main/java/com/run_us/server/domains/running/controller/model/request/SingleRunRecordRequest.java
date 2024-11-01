package com.run_us.server.domains.running.controller.model.request;

import com.run_us.server.domains.running.service.model.RunningAggregations;
import lombok.Getter;

@Getter
public class SingleRunRecordRequest {
  private final String recordData;
  private final int runningDistanceInMeters;
  private final int runningDurationInMilliSeconds;
  private final int averagePaceInMilliSeconds;

  public SingleRunRecordRequest(String recordData, int runningDistanceInMeters, int runningDurationInMilliSeconds, int averagePaceInMilliSeconds) {
    this.recordData = recordData;
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliSeconds = runningDurationInMilliSeconds;
    this.averagePaceInMilliSeconds = averagePaceInMilliSeconds;
  }

  public static RunningAggregations toRunningAggregations(SingleRunRecordRequest request) {
    return new RunningAggregations(
        request.getRecordData(),
        request.getRunningDistanceInMeters(),
        request.getRunningDurationInMilliSeconds(),
        request.getAveragePaceInMilliSeconds()
    );
  }
}
