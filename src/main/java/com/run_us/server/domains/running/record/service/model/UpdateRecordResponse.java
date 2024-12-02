package com.run_us.server.domains.running.record.service.model;

import com.run_us.server.domains.running.record.domain.RecordContent;
import com.run_us.server.domains.running.record.domain.RunRecord;
import lombok.Getter;

@Getter
public class UpdateRecordResponse {
  private final Long recordId;
  private final RecordContent recordContent;

  public UpdateRecordResponse(Long recordId, RecordContent recordContent) {
    this.recordId = recordId;
    this.recordContent = recordContent;
  }

  public static UpdateRecordResponse of(RunRecord runRecord) {
    return new UpdateRecordResponse(runRecord.getId(), runRecord.getRecordContent());
  }
}
