package com.run_us.server.domains.running.record.service.usecases;

import com.run_us.server.domains.running.record.domain.RecordContent;
import com.run_us.server.domains.running.record.domain.RecordStats;
import com.run_us.server.domains.running.record.domain.RunRecord;
import com.run_us.server.domains.running.record.service.RecordCommandService;
import com.run_us.server.domains.running.record.service.model.SaveRunRecordResponse;
import com.run_us.server.domains.running.record.service.model.UpdateRecordResponse;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordCommandUseCaseImpl implements RecordCommandUseCase {
  private final UserService userService;
  private final RunQueryService runQueryService;
  private final RecordCommandService recordCommandService;

  @Override
  public SuccessResponse<SaveRunRecordResponse> saveSingleRunRecordStats(
      String userId, RecordStats recordStats) {
    User user = userService.getUserByPublicId(userId);
    user.updateUserRunningInfo(
        recordStats.getRunningDistanceInMeters(), recordStats.getRunningTimeInSeconds());
    RunRecord runRecord = recordCommandService.saveSingleRunRecord(user.getId(), recordStats);
    return SuccessResponse.of(
        RunningHttpResponseCode.SINGLE_RUN_RECORD_SAVED, SaveRunRecordResponse.of(userId, runRecord));
  }

  @Override
  public SuccessResponse<SaveRunRecordResponse> saveGroupRunRecordStats(
      String userId, String runPublicId, RecordStats recordStats) {
    User user = userService.getUserByPublicId(userId);
    user.updateUserRunningInfo(
        recordStats.getRunningDistanceInMeters(), recordStats.getRunningTimeInSeconds());
    Run run = runQueryService.findByRunPublicId(runPublicId);
    RunRecord record =
        recordCommandService.saveGroupRunRecord(user.getId(), recordStats, run.getId());
    return SuccessResponse.of(
        RunningHttpResponseCode.GROUP_RUN_RECORD_SAVED, SaveRunRecordResponse.of(userId, record));
  }

  @Override
  public SuccessResponse<UpdateRecordResponse> updateRecordContent(
      String userId, Integer recordId, RecordContent recordContent) {
    User user = userService.getUserByPublicId(userId);
    RunRecord record =
        recordCommandService.updateRunRecordContent(user.getId(), recordId, recordContent);
    return SuccessResponse.of(
        RunningHttpResponseCode.RUN_PREVIEW_CREATED, UpdateRecordResponse.of(record));
  }
}
