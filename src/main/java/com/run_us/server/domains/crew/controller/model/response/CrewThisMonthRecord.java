package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.CrewMonthlyRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CrewThisMonthRecord {
    private Integer totalRunningCount;
    private Integer totalDistance;
    private Integer totalTime;

    @Builder
    CrewThisMonthRecord(Integer totalRunningCount, Integer totalDistance, Integer totalTime){
        this.totalRunningCount = totalRunningCount;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
    }

    public static CrewThisMonthRecord from(CrewMonthlyRecord monthlyRecord){
        return CrewThisMonthRecord.builder()
                .totalRunningCount(monthlyRecord.getTotalRunningCount())
                .totalDistance(monthlyRecord.getTotalDistance())
                .totalTime(monthlyRecord.getTotalTime())
                .build();
    }
}
