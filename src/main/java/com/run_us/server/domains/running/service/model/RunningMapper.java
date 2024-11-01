package com.run_us.server.domains.running.service.model;

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
   * @param runningAggregations 러닝 기록
   */
  public static PersonalRecord toPersonalRecord(
      final Integer userId,
      final Integer runningId,
      final RunningAggregations runningAggregations) {
    return PersonalRecord.builder()
        .runningId(runningId)
        .userId(userId)
        .recordData(runningAggregations.getRecordData())
        .runningDistanceInMeters(runningAggregations.getRunningDistanceInMeters())
        .runningDurationInMilliseconds(runningAggregations.getRunningDurationInMilliSeconds())
        .averagePaceInMilliseconds(runningAggregations.getAveragePaceInMilliSeconds())
        .build();
  }
}
