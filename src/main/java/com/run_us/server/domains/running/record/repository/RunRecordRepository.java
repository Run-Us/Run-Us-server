package com.run_us.server.domains.running.record.repository;

import com.run_us.server.domains.running.record.domain.RunRecord;
import com.run_us.server.domains.running.record.service.model.DailyRecordStats;
import com.run_us.server.domains.running.record.service.model.MonthlyRecordStats;
import com.run_us.server.domains.running.record.service.model.RecordStatsAggregation;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRecordRepository extends JpaRepository<RunRecord, Long> {
  Optional<RunRecord> findById(Long id);

  Slice<RunRecord> findAllByUserId(Integer userId, Pageable pageable);

  List<RunRecord> findAllByRunId(Integer runId);

  @Query("select r from RunRecord r " + "where r.userId = :userId " + "ORDER BY r.createdAt DESC ")
  Slice<RunRecord> findPageableRunRecords(Integer userId, Pageable pageable);

  @Query(
      "select r from RunRecord r "
          + "where r.userId = :userId "
          + "and r.createdAt between :start and :end "
          + "ORDER BY r.createdAt DESC ")
  Slice<RunRecord> findPageableRunRecordsInTimeRange(
      Integer userId, Pageable pageable, ZonedDateTime start, ZonedDateTime end);

  @Query(
      "SELECT NEW com.run_us.server.domains.running.record.service.model.RecordStatsAggregation("
          + "count (1), "
          + "SUM(r.recordStat.runningDistanceInMeters), "
          + "SUM(r.recordStat.runningTimeInSeconds)) "
          + "FROM RunRecord r "
          + "WHERE r.userId = :userId "
          + "AND r.createdAt BETWEEN :start AND :end")
  RecordStatsAggregation getRunRecordStatsAggregationInTimeRange(
      Integer userId, ZonedDateTime start, ZonedDateTime end);

  @Query("SELECT r FROM RunRecord r WHERE r.userId = :userId AND r.runId = :runId")
  Optional<RunRecord> findAllByUserIdAndRunId(Integer userId, Integer runId);

  @Query(
      value =
          "SELECT CAST(r.created_at AS DATE) AS date, "
              + "SUM(r.running_distance_in_meters) AS total_distance, "
              + "SUM(r.running_time_in_seconds) AS total_time "
              + "FROM run_record r "
              + "WHERE r.user_id = :userId "
              + "AND r.created_at BETWEEN :start AND :end "
              + "GROUP BY CAST(r.created_at AS DATE)",
      nativeQuery = true)
  List<DailyRecordStats> getDailyRunDistanceInTimeRange(
      Integer userId, ZonedDateTime start, ZonedDateTime end);

  @Query(
      value =
          "SELECT "
              + "DATE_FORMAT(r.created_at, '%Y-%m-01') AS date, "
              + "SUM(r.running_distance_in_meters) AS total_distance, "
              + "SUM(r.running_time_in_seconds) AS total_time "
              + "FROM run_record r "
              + "WHERE r.user_id = :userId "
              + "AND r.created_at BETWEEN :start AND :end "
              + "GROUP BY DATE_FORMAT(r.created_at, '%Y-%m-01') "
              + "ORDER BY DATE_FORMAT(r.created_at, '%Y-%m-01');",
      nativeQuery = true)
  List<MonthlyRecordStats> getMonthlyRunDistancesOfYear(
      Integer userId, ZonedDateTime start, ZonedDateTime end);

  @Query("""
        SELECT r.userId, SUM(r.recordStat.runningDistanceInMeters)
        FROM RunRecord r
        WHERE r.userId IN :userIds
        GROUP BY r.userId
    """)
  Map<Integer, Integer> findTotalDistanceByUserIds(List<Integer> userIds);
}
