package com.run_us.server.domains.crew.repository;

import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface CrewJoinRequestRepository extends JpaRepository<CrewJoinRequest, Integer> {

  Optional<CrewJoinRequest> findById(Integer id);

  Slice<CrewJoinRequest> findAllByCrewId(Integer crewId, PageRequest pageRequest);

  Optional<CrewJoinRequest> findByCrewIdAndUserId(Integer crewId, Integer userId);

  @Query("SELECT jr from CrewJoinRequest jr "
      + "WHERE jr.crewId = :crewId AND jr.userId = :userId "
      + "AND jr.requestedAt > :recentDate")
  Optional<CrewJoinRequest> findByCrewIdAndUserIdAndAfterRequestedAt(
      Integer crewId,
      Integer userId,
      ZonedDateTime recentDate
  );
}
