package com.run_us.server.domains.running.repository;

import com.run_us.server.domains.running.domain.record.PersonalRecord;
import com.run_us.server.domains.running.service.model.PersonalRecordQueryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Long> {

    @Query("SELECT new com.run_us.server.domains.running.service.model.PersonalRecordQueryResult(" +
            "p.runningDistanceInMeters, " +
            "p.runningDurationInMilliseconds, " +
            "p.averagePaceInMilliseconds, " +
            "p.createdAt) " +
            "FROM PersonalRecord p " +
            "WHERE p.userId = :userId AND p.runningId = :runningId")
    Optional<PersonalRecordQueryResult> findByUserIdAndRunningId(Long userId, Long runningId);

    @Query("SELECT new com.run_us.server.domains.running.service.model.PersonalRecordQueryResult(" +
            "p.runningDistanceInMeters, " +
            "p.runningDurationInMilliseconds, " +
            "p.averagePaceInMilliseconds, " +
            "p.createdAt) " +
            "FROM PersonalRecord p " +
            "WHERE p.userId = :userId")
    List<PersonalRecordQueryResult> findAllByUserId(Long userId);
}
