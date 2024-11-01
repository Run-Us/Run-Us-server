package com.run_us.server.domains.running.controller.model.request;

import lombok.Getter;

@Getter
public abstract class RecordRequest {

  protected String recordData;
  protected int runningDistanceInMeters;
  protected int runningDurationInMilliSeconds;
  protected int averagePaceInMilliSeconds;

  protected RecordRequest(String recordData, int runningDistanceInMeters, int runningDurationInMilliSecond, int averagePaceInMilliSecond) {
    this.recordData = recordData;
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliSeconds = runningDurationInMilliSecond;
    this.averagePaceInMilliSeconds = averagePaceInMilliSecond;
  }
}
