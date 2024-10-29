package com.run_us.server.domains.running.domain.record;

import com.run_us.server.global.common.DateAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "running_records")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "running_id", nullable = false)
  private Integer runningId;

  @Column(name = "start_time", nullable = false)
  private ZonedDateTime startTime;

  @Column(name = "end_time", nullable = false)
  private ZonedDateTime endTime;

  @Column(name = "participant_count", nullable = false)
  private int participantCount;

  @Column(name = "finisher_count", nullable = false)
  private int finishCount;

  @Column(name = "total_distance", nullable = false)
  private Integer totalDistanceInMeter;

  /***
   * Constructor for RunningRecord
   * @param runningId 참조하는 러닝의 id
   * @param startTime 기록 시작 시간
   * @param endTime 기록 종료 시간
   * @param participantCount 참가자 수
   * @param finishCount 완주자 수
   * @param totalDistanceInMeter 총 거리
   */
  @Builder
  public RunningRecord(
      @NotNull Integer runningId,
      @NotNull ZonedDateTime startTime,
      @NotNull ZonedDateTime endTime,
      int participantCount,
      int finishCount,
      @NotNull Integer totalDistanceInMeter) {
    validateEndTime(startTime, endTime);
    validateTotalDistance(totalDistanceInMeter);
    validateParticipantCount(participantCount);
    validateFinishCount(finishCount, participantCount);
    this.runningId = runningId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.participantCount = participantCount;
    this.finishCount = finishCount;
    this.totalDistanceInMeter = totalDistanceInMeter;
  }

  private void validateFinishCount(int finishCount, int participantCount) {
    if (finishCount < 0) {
      throw new IllegalArgumentException("완주자 수는 0 이상이어야 합니다.");
    }
    if (finishCount > participantCount) {
      throw new IllegalArgumentException("완주자 수는 참가자 수보다 많을 수 없습니다.");
    }
  }

  private void validateParticipantCount(int participantCount) {
    if (participantCount < 0) {
      throw new IllegalArgumentException("참가자 수는 0 이상이어야 합니다.");
    }
  }

  private void validateEndTime(ZonedDateTime startTime, ZonedDateTime endTime) {
    if (endTime.isBefore(startTime)) {
      throw new IllegalArgumentException("종료 시간은 시작 시간보다 늦어야 합니다.");
    }
  }

  private void validateTotalDistance(Integer totalDistanceInMeter) {
    if (totalDistanceInMeter < 0) {
      throw new IllegalArgumentException("총 거리는 0 이상이어야 합니다.");
    }
  }
}
