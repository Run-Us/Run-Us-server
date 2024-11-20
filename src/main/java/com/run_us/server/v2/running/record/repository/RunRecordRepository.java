package com.run_us.server.v2.running.record.repository;

import com.run_us.server.v2.running.record.domain.RunRecord;
import com.run_us.server.v2.running.record.service.model.RecordStatsAggregation;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRecordRepository extends JpaRepository<RunRecord, Integer> {
  Optional<RunRecord> findById(Integer id);

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
      "SELECT NEW com.run_us.server.v2.running.record.service.model.RecordStatsAggregation("
          + "SUM(r.recordStat.runningDistanceInMeters), "
          + "SUM(r.recordStat.runningTimeInSeconds)) "
          + "FROM RunRecord r "
          + "WHERE r.userId = :userId "
          + "AND r.createdAt BETWEEN :start AND :end")
  RecordStatsAggregation getRunRecordStatsAggregationInTimeRange(
      Integer userId, ZonedDateTime start, ZonedDateTime end);

  @Query("SELECT r FROM RunRecord r WHERE r.userId = :userId AND r.runId = :runId")
  Optional<RunRecord> findAllByUserIdAndRunId(Integer userId, Integer runId);
}
