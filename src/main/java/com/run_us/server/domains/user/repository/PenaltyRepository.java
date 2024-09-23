package com.run_us.server.domains.user.repository;

import com.run_us.server.domains.user.model.Penalty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

  @Query("SELECT p FROM Penalty p WHERE p.user.id = :userId")
  List<Penalty> findByUserId(Long userId);
}
