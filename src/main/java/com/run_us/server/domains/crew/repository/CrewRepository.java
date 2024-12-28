package com.run_us.server.domains.crew.repository;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
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

    @Query("SELECT EXISTS (SELECT 1 FROM Crew c JOIN c.crewMemberships m " +
            "WHERE c.id = :crewId AND m.userId = :userId)")
    boolean existsMembershipByCrewIdAndUserId(Integer crewId, Integer userId);

    @Query("SELECT EXISTS (SELECT 1 FROM Crew c JOIN c.joinRequests r " +
            "WHERE c.id = :crewId AND r.userId = :userId " +
            "AND r.status = 'WAITING')")
    boolean existsWaitingRequestByCrewIdAndUserId(Integer crewId, Integer userId);

    @Query("SELECT EXISTS (SELECT 1 FROM Crew c JOIN c.joinRequests r " +
            "WHERE c.id = :crewId AND r.userId = :userId " +
            "AND r.status = 'REJECTED' " +
            "AND r.processedAt > :recentDate)")
    boolean existsRecentRejectedRequestByCrewIdAndUserId(
            Integer crewId,
            Integer userId,
            LocalDateTime recentDate
    );

    @Query("SELECT jr FROM Crew c " +
            "JOIN c.joinRequests jr " +
            "WHERE c.publicId = :publicId " +
            "AND jr.status = 'WAITING' " +
            "ORDER BY jr.requestedAt DESC")
    List<CrewJoinRequest> findJoinRequestsByCrew(
            @Param("crewId") String publicId,
            PageRequest pageRequest
    );
}