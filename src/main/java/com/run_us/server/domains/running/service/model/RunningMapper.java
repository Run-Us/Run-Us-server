package com.run_us.server.domains.running.service.model;

import com.run_us.server.domains.running.controller.model.request.RunningRequest;
import com.run_us.server.domains.running.domain.record.PersonalRecord;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RunningMapper {

  public static RunningAggregation toRunningAggregation(RunningRequest.AggregateRunning aggregateRunning) {
    return RunningAggregation.builder()
        .dataList(aggregateRunning.getDataList())
        .count(aggregateRunning.getCount())
        .runningDistanceInMeter(aggregateRunning.getRunningDistanceInMeter())
        .runningDurationInMilliSecond(aggregateRunning.getRunningDurationInMilliSecond())
        .averagePaceInMilliSecond(aggregateRunning.getAveragePaceInMilliSecond())
        .build();
  }

  /***
   * 개인기록 도메인 객체로 매핑하는 메서드
   *
   * @param userId 유저 id
   * @param runningId 러닝 id
   * @param aggregateRunning 러닝결과 집계
   */
  public static PersonalRecord toPersonalRecord(
          final Integer userId,
          final Integer runningId,
          final RunningAggregation aggregateRunning) {
    return PersonalRecord.builder()
        .runningId(runningId)
        .userId(userId)
        .recordData(aggregateRunning.getDataList().stream().map(e->e.getRunnerPos().toString()).collect(Collectors.joining(",")))
        .runningDistanceInMeters(aggregateRunning.getRunningDistanceInMeter())
        .runningDurationInMilliseconds(aggregateRunning.getRunningDurationInMilliSecond())
        .averagePaceInMilliseconds(aggregateRunning.getAveragePaceInMilliSecond())
        .build();
  }
}
