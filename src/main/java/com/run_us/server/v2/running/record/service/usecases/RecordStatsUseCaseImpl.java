package com.run_us.server.v2.running.record.service.usecases;

import static com.run_us.server.v2.running.run.service.DateUtils.*;

import com.run_us.server.v2.running.record.service.RecordQueryService;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordStatsUseCaseImpl implements RecordStatsUseCase {

  private final RecordQueryService recordQueryService;

  @Override
  public void getUserWeeklyStats(Integer userId) {
    recordQueryService.getRunRecordStatsAggregationInTimeRange(
        userId, getFirstDayOfThisWeek(), ZonedDateTime.now());
  }

  @Override
  public void getUserMonthlyStats(Integer userId) {
    recordQueryService.getRunRecordStatsAggregationInTimeRange(
        userId, getFirstDayOfThisMonth(), ZonedDateTime.now());
  }

  @Override
  public void getUserYealyStats(Integer userId) {
    recordQueryService.getRunRecordStatsAggregationInTimeRange(
        userId, getFirstDayOfThisYear(), ZonedDateTime.now());
  }
}
