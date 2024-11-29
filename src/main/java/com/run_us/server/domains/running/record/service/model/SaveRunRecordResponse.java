package com.run_us.server.domains.running.record.service.model;

import com.run_us.server.domains.running.record.domain.RecordContent;
import com.run_us.server.domains.running.record.domain.RecordStats;
import com.run_us.server.domains.running.record.domain.RunRecord;

public class SaveRunRecordResponse {

  private final Long recordId;
  private final String userPublicId;
  private final RecordStats recordStats;

  private final RecordContent recordContent;

  public SaveRunRecordResponse(
      Long recordId, String userPublicId, RecordStats recordStats, RecordContent recordContent) {
    this.recordId = recordId;
    this.userPublicId = userPublicId;
    this.recordStats = recordStats;
    this.recordContent = recordContent;
  }

  public static SaveRunRecordResponse of(String userPublicId, RunRecord runRecord) {
    return new SaveRunRecordResponse(
        runRecord.getId(), userPublicId, runRecord.getRecordStat(), runRecord.getRecordContent());
  }
}
