package com.run_us.server.domains.running.record.domain;

import com.run_us.server.global.common.CreationTimeAudit;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunRecord extends CreationTimeAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer userId;

  private Integer runId;

  @Embedded private RecordStats recordStat;

  @Embedded private RecordContent recordContent;

  public RunRecord(Integer userId, Integer runId, RecordStats recordStat) {
    this.userId = userId;
    this.runId = runId;
    this.recordStat = recordStat;
  }

  @Transient
  public LocalDate getCreatedDate() {
    return createdAt.toLocalDate();
  }

  public void modifyContent(RecordContent content) {
    this.recordContent = content;
  }
}
