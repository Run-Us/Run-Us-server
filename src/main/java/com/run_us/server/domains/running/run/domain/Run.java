package com.run_us.server.domains.running.run.domain;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.global.common.CreationTimeAudit;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

import static com.run_us.server.domains.running.common.RunningConst.LIVE_SESSION_ALLOW_ALL_TIME;
import static com.run_us.server.domains.running.common.RunningConst.MAX_LIVE_SESSION_CREATION_TIME;

@Entity
@Table(name = "run")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Run extends CreationTimeAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "run_id")
  private Integer id;

  @Column(name = "run_host_id")
  private Integer hostId;

  @Column(name = "session_host_id")
  private Integer sessionHostId;

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
    this.sessionHostId = hostId;
    this.status = RunStatus.WAITING;
  }

  public Run (Integer hostId, Integer crewId) {
    this.hostId = hostId;
    this.sessionHostId = hostId;
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

  public boolean isCreationTimeOver() {
    return this.preview.getBeginTime()
        .plusMinutes(MAX_LIVE_SESSION_CREATION_TIME)
        .isBefore(ZonedDateTime.now());
  }

  public void openLiveSession(Integer userId) {
    validateRunModifiable();
    this.sessionHostId = userId;
    this.status = RunStatus.RUNNING;
  }

  public void modifyPaceInfo(List<RunPace> runPaces) {
    this.paceCategories = runPaces;
  }

  public void exposeToCrew(Integer crewPublicId) {
    validateRunModifiable();
    this.crewId = crewPublicId;
  }

  public boolean isLiveSessionCreatableByHost() {
    return ZonedDateTime.now().isAfter(this.preview.getBeginTime().minusMinutes(10));
  }

  public boolean isLiveSessionCreatableByAnyone() {
    return ZonedDateTime.now().isAfter(this.getPreview().getBeginTime().plusMinutes(LIVE_SESSION_ALLOW_ALL_TIME));
  }

  private void validateRunModifiable() {
    if (this.status == RunStatus.FINISHED)
      throw RunningException.of(RunningErrorCode.RUNNING_ALREADY_FINISHED);
    if (this.status == RunStatus.CANCELLED)
      throw RunningException.of(RunningErrorCode.RUNNING_CANCELED);
  }
}
