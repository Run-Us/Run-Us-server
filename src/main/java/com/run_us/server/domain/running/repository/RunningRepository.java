package com.run_us.server.domain.running.repository;

import com.run_us.server.domain.running.model.Running;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunningRepository extends JpaRepository<Running, Long> {
}
