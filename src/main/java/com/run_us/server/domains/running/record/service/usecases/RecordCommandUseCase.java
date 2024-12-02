package com.run_us.server.domains.running.record.service.usecases;

import com.run_us.server.domains.running.record.domain.RecordContent;
import com.run_us.server.domains.running.record.domain.RecordStats;
import com.run_us.server.domains.running.record.service.model.SaveRunRecordResponse;
import com.run_us.server.domains.running.record.service.model.UpdateRecordResponse;
import com.run_us.server.global.common.SuccessResponse;

public interface RecordCommandUseCase {
  SuccessResponse<SaveRunRecordResponse> saveSingleRunRecordStats(
      String userId, RecordStats recordStats);

  SuccessResponse<SaveRunRecordResponse> saveGroupRunRecordStats(
      String userId, String runPublicId, RecordStats recordStats);

  SuccessResponse<UpdateRecordResponse> updateRecordContent(
      String userId, Long recordId, RecordContent recordContent);
}
