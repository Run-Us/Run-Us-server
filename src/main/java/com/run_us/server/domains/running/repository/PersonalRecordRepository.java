package com.run_us.server.domains.running.repository;

import com.run_us.server.domains.running.domain.record.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Long> {

    Optional<PersonalRecord> findByUserIdAndRunningId(Long userId, Long runningId);

    List<PersonalRecord> findAllByUserId(Long userId);
}
