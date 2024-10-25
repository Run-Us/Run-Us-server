package com.run_us.server.domains.running.domain.record;

import com.run_us.server.global.common.CreationTimeAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.AttributeOverride;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "personal_records")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "createdAt", column = @Column(name = "uploaded_at"))
public class PersonalRecord extends CreationTimeAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "INT")
  private Integer id;

  @Column(name = "running_id", nullable = false, columnDefinition = "INT")
  private Integer runningId;

  @Column(name = "user_id", nullable = false, columnDefinition = "INT")
  private Integer userId;

  @Lob
  @Column(name = "record_data", nullable = false, columnDefinition = "MEDIUMBLOB")
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
      Integer runningId,
      Integer userId,
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
