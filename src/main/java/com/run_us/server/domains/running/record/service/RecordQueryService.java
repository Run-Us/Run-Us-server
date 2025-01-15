package com.run_us.server.domains.running.record.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.record.domain.RunRecord;
import com.run_us.server.domains.running.record.repository.RunRecordRepository;
import com.run_us.server.domains.running.record.service.model.DailyRecordStats;
import com.run_us.server.domains.running.record.service.model.MonthlyRecordStats;
import com.run_us.server.domains.running.record.service.model.RecordStatsAggregation;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordQueryService {

  private final RunRecordRepository runRecordRepository;

  public RunRecord findRunRecordById(Long recordId) {
    return runRecordRepository
        .findById(recordId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
  }

  public RunRecord findRunRecordByUserIdAndRunId(Integer userId, Integer runId) {
    return runRecordRepository
        .findAllByUserIdAndRunId(userId, runId)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
  }

  public List<RunRecord> findLatestRunRecordByUserId(Integer userId, int k) {
    Pageable pageable = PageRequest.of(0, k);
    return runRecordRepository.findPageableRunRecords(userId, pageable).getContent().stream()
        .toList();
  }

  public List<RunRecord> findAllRunRecordByUserId(Integer userId, int n, int k) {
    Pageable pageable = PageRequest.of(n, k);
    return runRecordRepository.findAllByUserId(userId, pageable).getContent();
  }

  public List<RunRecord> findRunRecordInTimeRange(
      Integer userId, int n, int k, ZonedDateTime start, ZonedDateTime end) {
    Pageable pageable = PageRequest.of(n, k);
    return runRecordRepository
        .findPageableRunRecordsInTimeRange(userId, pageable, start, end)
        .getContent();
  }

  public RecordStatsAggregation getWeeklyRunRecordStatsAggregationInTimeRange(
      Integer userId, ZonedDateTime start, ZonedDateTime end) {
    return runRecordRepository.getRunRecordStatsAggregationInTimeRange(userId, start, end);
  }

  public RecordStatsAggregation getMonthlyRunRecordStatsAggregationInTimeRange(
      Integer userId, ZonedDateTime start, ZonedDateTime end) {
    return runRecordRepository.getRunRecordStatsAggregationInTimeRange(userId, start, end);
  }

  public List<DailyRecordStats> getDailyRunDistanceInTimeRange(
      Integer userId, ZonedDateTime start, ZonedDateTime end) {
    return runRecordRepository.getDailyRunDistanceInTimeRange(userId, start, end);
  }

  public List<MonthlyRecordStats> getMonthlyRunDistancesOfYear(
      Integer userId, ZonedDateTime start, ZonedDateTime end) {
    return runRecordRepository.getMonthlyRunDistancesOfYear(userId, start, end);
  }

  public Map<Integer, Integer> getTotalDistanceMapByUserIds(List<Integer> userIds) {
    return runRecordRepository.findTotalDistanceByUserIds(userIds);
  }
}
