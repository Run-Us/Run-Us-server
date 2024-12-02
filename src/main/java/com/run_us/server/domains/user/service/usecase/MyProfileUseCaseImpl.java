package com.run_us.server.domains.user.service.usecase;

import com.run_us.server.domains.running.record.service.RecordQueryService;
import com.run_us.server.domains.running.record.service.model.DailyRecordStats;
import com.run_us.server.domains.running.record.service.model.MonthlyRecordStats;
import com.run_us.server.domains.running.record.service.model.RecordPost;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.domains.user.service.model.DistanceListMapper;
import com.run_us.server.domains.user.service.model.MyPageResult;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyProfileUseCaseImpl implements MyProfileUseCase {

  private final RecordQueryService recordQueryService;
  private final UserService userService;

  @Override
  public MyPageResult getMyPage(String userId) {
    User user = userService.getUserByPublicId(userId);
    List<RecordPost> runningRecords =
        RecordPost.fromRunRecords(recordQueryService.findLatestRunRecordByUserId(user.getId(), 3));
    return MyPageResult.of(user.getProfile(), runningRecords);
  }

  @Override
  public List<Long> getMyPageDistanceStatistics(
      String type, String userId, ZonedDateTime startDate, ZonedDateTime endDate) {
    User user = userService.getUserByPublicId(userId);
    return getDistanceListWithType(type, user.getId(), startDate, endDate);
  }

  private List<Long> getDistanceListWithType(
      String type, Integer id, ZonedDateTime startDate, ZonedDateTime endDate) {
    return switch (type) {
      case "weekly" -> getWeeklyRunDistance(id, startDate, endDate);
      case "monthly" -> getMonthlyRunDistance(id, startDate, endDate);
      default -> getYearlyRunDistance(id, startDate, endDate);
    };
  }

  private List<Long> getWeeklyRunDistance(Integer id, ZonedDateTime startDate, ZonedDateTime endDate) {
    List<DailyRecordStats> dailyRecordStatsList =
        recordQueryService.getDailyRunDistanceInTimeRange(id, startDate, endDate);
    return DistanceListMapper.getWeeklyRunDistanceInTimeRange(
        dailyRecordStatsList, startDate, endDate);
  }

  private List<Long> getMonthlyRunDistance(Integer id, ZonedDateTime startDate, ZonedDateTime endDate) {
    List<DailyRecordStats> dailyRecordStatsList =
        recordQueryService.getDailyRunDistanceInTimeRange(id, startDate, endDate);
    return DistanceListMapper.getMonthlyRunDistanceInTimeRange(
        dailyRecordStatsList, startDate, endDate);
  }

  private List<Long> getYearlyRunDistance(Integer id, ZonedDateTime startDate, ZonedDateTime endDate) {
    List<MonthlyRecordStats> monthlyRecordStatsList =
        recordQueryService.getMonthlyRunDistancesOfYear(id, startDate, endDate);
    return DistanceListMapper.getYearlyRunDistanceInTimeRange(
        monthlyRecordStatsList, startDate, endDate);
  }
}
