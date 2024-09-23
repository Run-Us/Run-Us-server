package com.run_us.server.domains.running.domain;

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

  @Enumerated(EnumType.STRING)
  private RunningType runningType;

  @Builder
  public PersonalRecord(Long runningId, Long userId, String recordData, RunningType runningType) {
    this.runningId = runningId;
    this.userId = userId;
    this.recordData = recordData;
    this.runningType = runningType;
  }
}
