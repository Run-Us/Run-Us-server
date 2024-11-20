package com.run_us.server.v2.running.record.service;

import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.v2.RunningException;
import com.run_us.server.v2.running.record.domain.RunRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordValidator {

  public void validateIsRecordOwner(Integer userId, RunRecord runRecord) {
    if (!runRecord.getUserId().equals(userId)) {
      throw RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND);
    }
  }
}
