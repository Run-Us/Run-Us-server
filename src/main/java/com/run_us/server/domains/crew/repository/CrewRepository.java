package com.run_us.server.domains.crew.repository;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewMembership;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    Optional<Crew> findByPublicId(String publicId);

    @Query("SELECT EXISTS (SELECT 1 FROM Crew c JOIN c.crewMemberships m " +
            "WHERE c.id = :crewId AND m.userId = :userId)")
    boolean existsMembershipByCrewIdAndUserId(Integer crewId, Integer userId);

    @Query("SELECT m FROM Crew c JOIN c.crewMemberships m " +
        "WHERE c.id = :crewId " +
        "ORDER BY m.joinedAt DESC")
    List<CrewMembership> findMembershipsByCrewId(
        Integer crewId,
        PageRequest pageRequest
    );
}