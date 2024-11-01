package com.run_us.server.domains.running.controller.model.request;

import lombok.Getter;

@Getter
public class MultiRunRecordRequest extends RecordRequest {

  private final String runningId;

  public MultiRunRecordRequest(
      String recordData,
      int runningDistanceInMeter,
      int runningDurationInMilliSecond,
      int averagePaceInMilliSecond,
      String runningId) {
    super(recordData, runningDistanceInMeter, runningDurationInMilliSecond, averagePaceInMilliSecond);
    this.runningId = runningId;
  }
}
