package com.run_us.server.v2.running.record.service.usecases;

import com.run_us.server.v2.running.record.domain.RecordContent;
import com.run_us.server.v2.running.record.domain.RecordStats;

public interface RecordCommandUseCase {
  void saveSingleRunRecordStats(String userId, RecordStats recordStats);

  void saveGroupRunRecordStats(String userId, String runPublicId, RecordStats recordStats);

  void updateRecordContent(String userId, Integer recordId, RecordContent recordContent);
}
