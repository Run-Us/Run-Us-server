package com.run_us.server.domains.running.controller.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SingleRunRecordRequest extends RecordRequest{

  @Builder
  public SingleRunRecordRequest(
      String recordData,
      int runningDistanceInMeters,
      int runningDurationInMilliSeconds,
      int averagePaceInMilliSeconds) {
    super(recordData, runningDistanceInMeters, runningDurationInMilliSeconds, averagePaceInMilliSeconds);
  }
}
