package com.run_us.server.domains.running.service.model;

import com.run_us.server.domains.running.controller.model.request.RecordRequest;
import com.run_us.server.domains.running.domain.record.PersonalRecord;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RunningMapper {

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
      final RecordRequest aggregateRunning) {
    return PersonalRecord.builder()
        .runningId(runningId)
        .userId(userId)
        .recordData(aggregateRunning.getRecordData())
        .runningDistanceInMeters(aggregateRunning.getRunningDistanceInMeters())
        .runningDurationInMilliseconds(aggregateRunning.getRunningDurationInMilliSeconds())
        .averagePaceInMilliseconds(aggregateRunning.getAveragePaceInMilliSeconds())
        .build();
  }
}
