package com.run_us.server.v2.running.record.service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// RecordStatsAggregation은 일정 기간에 대한 러닝통계를 추산할때 사용하는 DTO입니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordStatsAggregation {
  private Long totalDistance;
  private Long totalTime;

  public RecordStatsAggregation(Long totalDistance, Long totalTime) {
    this.totalDistance = totalDistance;
    this.totalTime = totalTime;
  }
}
