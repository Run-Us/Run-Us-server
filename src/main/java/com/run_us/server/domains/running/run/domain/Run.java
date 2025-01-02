package com.run_us.server.domains.running.run.domain;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.global.common.CreationTimeAudit;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

  private Integer crewId;

  @Enumerated(EnumType.STRING)
  private RunStatus status;

  @ElementCollection(targetClass = RunPace.class)
  @CollectionTable(name = "run_pace", joinColumns = @JoinColumn(name = "run_id"))
  @Enumerated(EnumType.STRING)
  List<RunPace> paceCategories;

  @Embedded private RunningPreview preview;

  // 생성
  public Run(Integer hostId) {
    this.hostId = hostId;
    this.status = RunStatus.WAITING;
  }

  public Run (Integer hostId, Integer crewId) {
    this.hostId = hostId;
    this.crewId = crewId;
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

  public boolean isDeletable() {
    return RunStatus.isRunDeletable(this.status);
  }

  public boolean isJoinable() {
    return RunStatus.isJoinable(this.status);
  }

  public boolean isHost(int userId) {
    return this.hostId.equals(userId);
  }

  public void modifyPaceInfo(List<RunPace> runPaces) {
    this.paceCategories = runPaces;
  }

  public void exposeToCrew(Integer crewPublicId) {
    validateRunModifiable();
    this.crewId = crewPublicId;
  }

  private void validateRunModifiable() {
    if (this.status == RunStatus.FINISHED)
      throw RunningException.of(RunningErrorCode.RUNNING_ALREADY_FINISHED);
    if (this.status == RunStatus.CANCELLED)
      throw RunningException.of(RunningErrorCode.RUNNING_CANCELED);
  }
}
