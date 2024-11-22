package com.run_us.server.v2.running.record.service.usecases;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.v2.running.record.domain.RecordContent;
import com.run_us.server.v2.running.record.domain.RecordStats;
import com.run_us.server.v2.running.record.service.RecordCommandService;
import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.service.RunQueryService;
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
  public void saveSingleRunRecordStats(String userId, RecordStats recordStats) {
    User user = userService.getUserByPublicId(userId);
    user.updateUserRunningInfo(
        recordStats.getRunningDistanceInMeters(), recordStats.getRunningTimeInSeconds());
    recordCommandService.saveSingleRunRecord(user.getId(), recordStats);
  }

  @Override
  public void saveGroupRunRecordStats(String userId, String runPublicId, RecordStats recordStats) {
    User user = userService.getUserByPublicId(userId);
    user.updateUserRunningInfo(
        recordStats.getRunningDistanceInMeters(), recordStats.getRunningTimeInSeconds());
    Run run = runQueryService.findByRunPublicId(runPublicId);
    recordCommandService.saveGroupRunRecord(user.getId(), recordStats, run.getId());
  }

  @Override
  public void updateRecordContent(String userId, Integer recordId, RecordContent recordContent) {
    User user = userService.getUserByPublicId(userId);
    recordCommandService.updateRunRecordContent(user.getId(), recordId, recordContent);
  }
}
