package com.run_us.server.v2.running.run.domain;

import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.global.common.CreationTimeAudit;
import com.run_us.server.v2.RunningException;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "run")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Run extends CreationTimeAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "run_id")
  private Integer id;

  private Integer hostId;

  private String publicId;

  private String passcode;

  @Enumerated(EnumType.STRING)
  private RunStatus status;

  @Embedded private RunningPreview preview;

  // 생성
  public Run(Integer hostId) {
    this.hostId = hostId;
    this.status = RunStatus.WAITING;
  }

  @Override
  public void prePersist() {
    this.publicId = TSID.Factory.getTsid().toString();
  }

  // 수정
  public void changeStatus(RunStatus status) {
    validateRunModifiable();
    this.status = status;
  }

  public void modifySessionInfo(RunningPreview runningPreview) {
    validateRunModifiable();
    this.preview = runningPreview;
  }

  public boolean isHost(int userId) {
    return this.hostId == userId;
  }

  private void validateRunModifiable() {
    if (this.status == RunStatus.FINISHED)
      throw RunningException.of(RunningErrorCode.RUNNING_ALREADY_FINISHED);
    if (this.status == RunStatus.CANCELLED)
      throw RunningException.of(RunningErrorCode.RUNNING_CANCELED);
  }
}
