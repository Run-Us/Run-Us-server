package com.run_us.server.v2.running.record.service.usecases;

import static com.run_us.server.v2.running.run.service.DateUtils.*;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.v2.running.record.domain.RunRecord;
import com.run_us.server.v2.running.record.service.RecordQueryService;
import com.run_us.server.v2.running.record.service.model.RecordPost;
import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.service.RunQueryService;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordQueryUseCaseImpl implements RecordQueryUseCase {

  private final RecordQueryService recordQueryService;
  private final RunQueryService runQueryService;
  private final UserService userService;

  @Override
  public RecordPost getSingleRecordPost(String userId, String runId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runQueryService.findByRunPublicId(runId);
    return RecordPost.from(
        recordQueryService.findRunRecordByUserIdAndRunId(user.getId(), run.getId()));
  }

  /***
   * 특정 유저의 모든 기록 조회 메소드
   * @param userId
   * @param page
   * @param fetchSize
   * @return List<RecordPost> : RecordPost 리스트 반환
   */
  @Override
  public List<RecordPost> getUserRecordPosts(Integer userId, int page, int fetchSize) {
    return recordQueryService.findAllRunRecordByUserId(userId, page, fetchSize).stream()
        .map(RecordPost::from)
        .toList();
  }

  // 특정 유저의 주간, 월간, 연간 기록 조회 메소드
  @Override
  public List<RecordPost> getWeeklyRecordPosts(Integer userId, int page, int fetchSize) {
    return mapToRecordPost(recordQueryService.findRunRecordInTimeRange(userId, page, fetchSize, getFirstDayOfThisWeek(), ZonedDateTime.now()));

  }

  @Override
  public List<RecordPost> getMonthlyRecordPosts(Integer userId, int page, int fetchSize) {
    return mapToRecordPost(recordQueryService.findRunRecordInTimeRange(userId, page, fetchSize, getFirstDayOfThisMonth(), ZonedDateTime.now()));
  }

  @Override
  public List<RecordPost> getYearlyRecordPosts(Integer userId, int page, int fetchSize) {
    return mapToRecordPost(recordQueryService.findRunRecordInTimeRange(userId, page, fetchSize, getFirstDayOfThisYear(), ZonedDateTime.now()));
  }

  private List<RecordPost> mapToRecordPost(List<RunRecord> runRecords) {
    return runRecords.stream()
        .map(RecordPost::from)
        .toList();
  }
}
