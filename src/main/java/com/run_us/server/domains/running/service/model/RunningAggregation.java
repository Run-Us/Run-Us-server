package com.run_us.server.domains.running.service.model;

import com.run_us.server.domains.running.domain.LocationData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RunningAggregation {
    private final String runningId;
    private final String userId;
    private final List<LocationData> dataList;
    private final int count;
    private final int runningDistanceInMeter;
    private final int runningDurationInMilliSecond;
    private final int averagePaceInMilliSecond;

    @Builder
    public RunningAggregation(String runningId, String userId, List<LocationData> dataList, int count, int runningDistanceInMeter, int runningDurationInMilliSecond, int averagePaceInMilliSecond) {
        this.runningId = runningId;
        this.userId = userId;
        this.dataList = dataList;
        this.count = count;
        this.runningDistanceInMeter = runningDistanceInMeter;
        this.runningDurationInMilliSecond = runningDurationInMilliSecond;
        this.averagePaceInMilliSecond = averagePaceInMilliSecond;
    }
}
