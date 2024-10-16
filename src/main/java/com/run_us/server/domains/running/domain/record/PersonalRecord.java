package com.run_us.server.domains.running.domain.record;

import com.run_us.server.global.common.DateAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "personal_records")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalRecord extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "personal_record_id")
  private Long id;

  @Column(name = "running_id", nullable = false)
  private Long runningId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Lob
  @Column(name = "record_data", nullable = false)
  private String recordData;

  @Column(name = "distance")
  private Integer runningDistanceInMeters;

  @Column(name = "duration")
  private Integer runningDurationInMilliseconds;

  @Column(name = "avg_pace")
  private Integer averagePaceInMilliseconds;

  /***
   * 개인 기록 객체를 위한 생성자
   * @param runningId 러닝 id
   * @param userId 유저 id
   * @param recordData 기록 데이터
   * @param runningDistanceInMeters 러닝 거리(미터)
   * @param averagePaceInMilliseconds 평균 페이스(밀리초)
   * @param runningDurationInMilliseconds 러닝 시간(밀리초)
   */
  @Builder
  public PersonalRecord(
      Long runningId,
      Long userId,
      String recordData,
      Integer runningDistanceInMeters,
      Integer runningDurationInMilliseconds,
      Integer averagePaceInMilliseconds) {
    this.runningId = runningId;
    this.userId = userId;
    this.recordData = recordData;
    this.runningDistanceInMeters = runningDistanceInMeters;
    this.runningDurationInMilliseconds = runningDurationInMilliseconds;
    this.averagePaceInMilliseconds = averagePaceInMilliseconds;
  }
}
