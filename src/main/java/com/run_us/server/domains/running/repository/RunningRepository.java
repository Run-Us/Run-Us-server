package com.run_us.server.domains.running.repository;

import com.run_us.server.domains.running.domain.running.Running;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RunningRepository extends JpaRepository<Running, Long> {

  @Query("SELECT r FROM Running r WHERE r.publicKey = :publicKey")
  Optional<Running> findByPublicKey(String publicKey);
}
