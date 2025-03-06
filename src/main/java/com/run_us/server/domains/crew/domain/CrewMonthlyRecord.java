package com.run_us.server.domains.crew.domain;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrewMonthlyRecord {
    private int totalRunningCount;
    private int totalDistance;
    private int totalTime;

    @Builder
    CrewMonthlyRecord(int totalRunningCount, int totalDistance, int totalTime){
        this.totalRunningCount = totalRunningCount;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
    }

    public void addRecord(int addDistance, int addTime){
        this.totalRunningCount++;
        this.totalDistance += addDistance;
        this.totalTime += addTime;
    }
}
