package com.run_us.server.v2.running.record.service;

import com.run_us.server.v2.running.record.domain.RecordContent;
import com.run_us.server.v2.running.record.domain.RecordStats;
import com.run_us.server.v2.running.record.domain.RunRecord;
import com.run_us.server.v2.running.record.repository.RunRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordCommandService {

  private final RecordQueryService recordQueryService;
  private final RunRecordRepository recordRepository;
  private final RecordValidator recordValidator;

  public void saveSingleRunRecord(Integer userId, RecordStats stats) {
    RunRecord runRecord = new RunRecord(userId, null, stats);
    recordRepository.save(runRecord);
  }

  public void saveGroupRunRecord(Integer userId, RecordStats stats, Integer runId) {
    RunRecord runRecord = new RunRecord(userId, runId, stats);
    recordRepository.save(runRecord);
  }

  public void updateRunRecordContent(
      Integer userId, Integer recordId, RecordContent updatedContent) {
    RunRecord runRecord = recordQueryService.findRunRecordById(recordId);
    recordValidator.validateIsRecordOwner(userId, runRecord);
    runRecord.modifyContent(updatedContent);
  }
}
