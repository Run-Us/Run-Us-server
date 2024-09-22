package com.run_us.server.domains.running.repository;

import com.run_us.server.domains.running.domain.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Long> {

}
