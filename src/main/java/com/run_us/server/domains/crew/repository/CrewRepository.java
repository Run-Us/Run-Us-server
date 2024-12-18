package com.run_us.server.domains.crew.repository;

import com.run_us.server.domains.crew.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    Optional<Crew> findByPublicId(String publicId);
}