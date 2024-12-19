package com.run_us.server.domains.crew.repository;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    Optional<Crew> findByPublicId(String publicId);

    @Query("SELECT jr FROM Crew c " +
            "JOIN c.joinRequests jr " +
            "WHERE c.publicId = :publicId " +
            "AND jr.userId = :userInternalId " +
            "AND jr.status = 'WAITING'")
    Optional<CrewJoinRequest> findWaitingJoinRequest(
            @Param("crewId") String publicId,
            @Param("userId") Integer userInternalId
    );
}