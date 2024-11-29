package com.run_us.server.domains.running.record.service.usecases;

import static com.run_us.server.domains.running.run.service.DateUtils.*;

import com.run_us.server.domains.running.record.service.RecordQueryService;
import com.run_us.server.domains.running.record.service.model.RecordPost;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
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
  public SuccessResponse<RecordPost> getSingleRecordPost(String userId, String runId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runQueryService.findByRunPublicId(runId);
    return SuccessResponse.of(
        RunningHttpResponseCode.SINGLE_RECORD_FETCHED,
        RecordPost.fromRunRecords(
            recordQueryService.findRunRecordByUserIdAndRunId(user.getId(), run.getId())));
  }

  /***
   * 특정 유저의 모든 기록 조회 메소드
   * @param userId
   * @param page
   * @param fetchSize
   * @return List<RecordPost> : RecordPost 리스트 반환
   */
  @Override
  public SuccessResponse<List<RecordPost>> getUserRecordPosts(
      Integer userId, int page, int fetchSize) {
    return SuccessResponse.of(
        RunningHttpResponseCode.RUN_PREVIEW_FETCHED,
        RecordPost.fromRunRecords(
            recordQueryService.findAllRunRecordByUserId(userId, page, fetchSize)));
  }

  // 특정 유저의 주간, 월간, 연간 기록 조회 메소드
  @Override
  public SuccessResponse<List<RecordPost>> getWeeklyRecordPosts(
      Integer userId, int page, int fetchSize) {
    return SuccessResponse.of(
        RunningHttpResponseCode.RUN_PREVIEW_FETCHED,
        RecordPost.fromRunRecords(
            recordQueryService.findRunRecordInTimeRange(
                userId, page, fetchSize, getFirstDayOfThisWeek(), ZonedDateTime.now())));
  }

  @Override
  public SuccessResponse<List<RecordPost>> getMonthlyRecordPosts(
      Integer userId, int page, int fetchSize) {
    return SuccessResponse.of(
        RunningHttpResponseCode.RUN_PREVIEW_FETCHED,
        RecordPost.fromRunRecords(
            recordQueryService.findRunRecordInTimeRange(
                userId, page, fetchSize, getFirstDayOfThisMonth(), ZonedDateTime.now())));
  }

  @Override
  public SuccessResponse<List<RecordPost>> getYearlyRecordPosts(
      Integer userId, int page, int fetchSize) {
    return SuccessResponse.of(
        RunningHttpResponseCode.RUN_PREVIEW_FETCHED,
        RecordPost.fromRunRecords(
            recordQueryService.findRunRecordInTimeRange(
                userId, page, fetchSize, getFirstDayOfThisYear(), ZonedDateTime.now())));
  }
}
